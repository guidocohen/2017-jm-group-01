package fixture;

import java.util.ArrayList;
import java.util.List;
import modelo.Cuenta;
import modelo.Empresa;
import modelo.Periodo;
import modelo.TipoCuenta;

public class TestFixture {

    public static Empresa facebook() {
        Empresa facebook = new Empresa("Facebook");
        facebook.setEdad(13);
        facebook.setCuentas(cuentasEstandar(8162, 1487, 4273, 0, 169));
        return facebook;
    }

    public static Empresa google() {
        Empresa google = new Empresa("Google");

        google.setEdad(19);
        google.setCuentas(cuentasEstandar(24818, 30418, 3380, 200, 130));

        return google;
    }

    public static Empresa apple() {
        Empresa apple = new Empresa();

        List<Cuenta> cuentas = new ArrayList<>();
        cuentas.add(new Cuenta(TipoCuenta.EBITDA.name(), 84505, new Periodo("2015")));
        cuentas.add(new Cuenta(TipoCuenta.EBITDA.name(), 73333, new Periodo("2016")));
        cuentas.add(new Cuenta(TipoCuenta.INO_CONTINUAS.name(), 21120, new Periodo("2015")));
        cuentas.add(new Cuenta(TipoCuenta.FREE_CASH_FLOW.name(), 13844, new Periodo("2014")));
        cuentas.add(new Cuenta(TipoCuenta.FREE_CASH_FLOW.name(), 53394, new Periodo("2015")));

        apple.setEdad(41);
        apple.setCuentas(cuentas);

        return apple;
    }

    public static Empresa intel() {
        Empresa intel = new Empresa("Intel");

        List<Cuenta> cuentas = new ArrayList<>();
        cuentas.add(new Cuenta(TipoCuenta.EBITDA.name(), 21459, new Periodo("2016")));
        cuentas.add(new Cuenta(TipoCuenta.FREE_CASH_FLOW.name(), 12183, new Periodo("2015")));
        cuentas.add(new Cuenta(TipoCuenta.INO_CONTINUAS.name(), 3562, new Periodo("2016")));
        cuentas.add(new Cuenta(TipoCuenta.INO_DISCONTINUAS.name(), 110, new Periodo("2016")));

        intel.setEdad(49);
        intel.setCuentas(cuentas);

        return intel;
    }

    public static Periodo anio(Integer anio) {
        return new Periodo(anio.toString());
    }

    // este metodo sirve porque Google y Facebook tienen las mismas cuentas (con otros resultados)
    // cuentasEstandar son: EBITDA 2015, EBITDA 2016, INO_CONT e INO_DISCONT ambas 2016
    public static List<Cuenta> cuentasEstandar(int ebitda_15, int ebitda_16, int ino_cont, int ino_disc, int fds) {
        List<Cuenta> cuentas = new ArrayList<>();
        cuentas.add(new Cuenta(TipoCuenta.EBITDA.name(), ebitda_15, new Periodo("2015")));
        cuentas.add(new Cuenta(TipoCuenta.EBITDA.name(), ebitda_16, new Periodo("2016")));
        cuentas.add(new Cuenta(TipoCuenta.INO_CONTINUAS.name(), ino_cont, new Periodo("2016")));
        cuentas.add(new Cuenta(TipoCuenta.INO_DISCONTINUAS.name(), ino_disc, new Periodo("2016")));
        cuentas.add(new Cuenta(TipoCuenta.FDS.name(), fds, new Periodo("2016")));
        return cuentas;
    }

}
