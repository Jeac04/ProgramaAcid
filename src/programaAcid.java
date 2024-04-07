import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class programaAcid {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();

        Cliente cliente1 = new Cliente(inventario, "Cliente 1");
        Cliente cliente2 = new Cliente(inventario, "Cliente 2");

        Thread hiloCliente1 = new Thread(cliente1);
        Thread hiloCliente2 = new Thread(cliente2);

        hiloCliente1.start();
        hiloCliente2.start();
    }

    static class Inventario {
        private int cantidadProducto;
        private Lock lock;

        public Inventario() {
            this.cantidadProducto = 50; 
            this.lock = new ReentrantLock();
        }

        public void comprarProducto(int cantidad) {
            lock.lock();
            try {
                if (cantidadProducto >= cantidad) {
                    cantidadProducto -= cantidad;
                    System.out.println("Compra realizada. Cantidad actual en inventario: " + cantidadProducto);
                } else {
                    System.out.println("Producto agotado. No se puede realizar la compra.");
                }
            } finally {
                lock.unlock();
            }
        }

        public void reponerProducto(int cantidad) {
            lock.lock();
            try {
                cantidadProducto += cantidad;
                System.out.println("Inventario reabastecido. Cantidad actual en inventario: " + cantidadProducto);
            } finally {
                lock.unlock();
            }
        }
    }

    static class Cliente implements Runnable {
        private Inventario inventario;
        private String nombreCliente;

        public Cliente(Inventario inventario, String nombreCliente) {
            this.inventario = inventario;
            this.nombreCliente = nombreCliente;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                inventario.comprarProducto(10);
                inventario.reponerProducto(20);
            }
        }
    }
}
