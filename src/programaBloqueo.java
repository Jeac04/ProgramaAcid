import java.util.*;

public class programaBloqueo {

    public static void main(String[] args) {
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.startTransaction();
        try {
            Evento evento = new Evento("Concierto", 100);
            System.out.println("Tickets disponibles para el evento '" + evento.getNombre() + "': " + evento.getCapacidad());
            int cantidadReservada = 2;

            if (evento.getCapacidad() >= cantidadReservada) {
                // Simular fase de crecimiento de la transacción
                System.out.println("Iniciando la reserva de tickets...");
                Thread.sleep(2000); // Simulación de operación que toma tiempo
                evento.setCapacidad(evento.getCapacidad() - cantidadReservada);
                System.out.println("Reserva de tickets completada. Tickets restantes: " + evento.getCapacidad());

                // Simular fase de reducción de la transacción
                System.out.println("Confirmación de la reserva...");
                Thread.sleep(2000); // Simulación de operación que toma tiempo
                transactionManager.commitTransaction();
                System.out.println("Reserva confirmada. ¡Disfrute del evento!");
            } else {
                System.out.println("No hay suficientes tickets disponibles para la reserva.");
                transactionManager.rollbackTransaction();
                System.out.println("La reserva ha sido cancelada.");
            }
        } catch (InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            transactionManager.rollbackTransaction();
            System.out.println("La reserva ha sido cancelada debido a un error.");
        }
    }
}

class TransactionManager {
    private boolean inTransaction;

    public void startTransaction() {
        // Simulación de la fase de crecimiento del bloqueo de dos fases
        System.out.println("Iniciando transacción...");
        inTransaction = true;
    }

    public void commitTransaction() {
        // Simulación de la fase de reducción del bloqueo de dos fases
        System.out.println("Commit de la transacción...");
        inTransaction = false;
    }

    public void rollbackTransaction() {
        // Simulación de la cancelación de la transacción
        System.out.println("Rollback de la transacción...");
        inTransaction = false;
    }
}

class Evento {
    private String nombre;
    private int capacidad;

    public Evento(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
