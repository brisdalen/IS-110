
public class Main {
    public static void main(String[] args) {
        SupportSystem.info();
        System.out.println("Dette ble printet først.");
        
        SupportSystem supportSys = new SupportSystem();
        supportSys.start();
    }
}
