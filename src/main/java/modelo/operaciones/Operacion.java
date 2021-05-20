package modelo.operaciones;

public enum Operacion {
    SUMA("+", 1) {
        @Override
        public double calcular(double operando1, double operando2) {
            return operando1 + operando2;
        }
    },
    RESTA("-", 1) {
        @Override
        public double calcular(double operando1, double operando2) {
            return operando1 - operando2;
        }
    },
    MULTIPLICACION("*", 2) {
        @Override
        public double calcular(double operando1, double operando2) {
            return operando1 * operando2;
        }
    },
    DIVISION("/", 2) {
        @Override
        public double calcular(double operando1, double operando2) {
            if (operando2 == 0) {
                throw new ArithmeticException("No es posible dividir por 0");
            }
            return operando1 / operando2;
        }

        @Override
        public String toString() {
            return "/";
        }
    };

    private final String representacion;
    private final Integer precedencia;

    Operacion(String representacion, Integer precedencia) {
        this.representacion = representacion;
        this.precedencia = precedencia;
    }

    public Boolean menorPrecendenciaQue(Operacion otra) {
        return this.precedencia < otra.precedencia;
    }

    @Override
    public String toString() {
        return representacion;
    }

    public abstract double calcular(double operando1, double operando2);

}
