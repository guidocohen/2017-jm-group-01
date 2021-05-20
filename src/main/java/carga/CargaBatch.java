package carga;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import modelo.repositorios.persistencia.RepoDeEmpresasPersistencia;

public class CargaBatch {
	protected String directorio = "./src/test/resources/Empresas/";
	protected String path = null;

	public String mostrarArchivosDeUnDirectorio() throws IOException {
		Files.walk(Paths.get(directorio)).forEach(ruta -> {
			if (Files.isRegularFile(ruta)) {
				path = ruta.toString();
			}
		});
		return path;
	}

	public void cargarEmpresasBatch() {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(() -> {
			try {
				cargarEmpresas();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}, 0, 1, TimeUnit.DAYS);
	}

	private void cargarEmpresas() throws IOException {
		Path directoryToWatch = Paths.get(directorio);
		WatchService watchService = directoryToWatch.getFileSystem().newWatchService();
		directoryToWatch.register(watchService,
				new WatchEvent.Kind[] { StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY });

		try {

			WatchKey key = watchService.take();

			while (key != null) {
				List<WatchEvent<?>> eventos = key.pollEvents();
				String file = eventos.get(0).context().toString();
				String nuevaRuta = directorio + file;
				CargadorEmpresas cargador = new CargadorEmpresasBuilder().conArchivo(new File(nuevaRuta))
						.paraFormatoAdecuado();
				RepoDeEmpresasPersistencia.getInstancia().cargaInicial(cargador);
				key.reset();
				key = watchService.take();
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
