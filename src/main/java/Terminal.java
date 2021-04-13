import java.util.Map;

public class Terminal {

    public static String statusString(LiftSystem liftSystem){
        Map<Integer, Lift.LiftStatus> status = liftSystem.getStatus();
        StringBuilder res = new StringBuilder();
        res.append("Number of lifts: ").append(status.size()).append("\n");
        for (Integer id: status.keySet())
            res.append("liftId: ").append(id).append(", ").append(status.get(id).toString()).append("\n");
        return res.toString();
    }

    public static void main(String[] args) {
        LiftSystem liftSystem = new LiftSystem();
        boolean isRunning = true;

        if (System.console() == null){
            System.out.println("Program needs to be run from a interactive terminal");
            isRunning = false;
        }

        while (isRunning) {
            String s = System.console().readLine();
            String[] input = s.split(" ");
            String command = input[0];
            try {
                switch (command) {
                    case "addLift":
                        if (liftSystem.addLift(Integer.parseInt(input[1]), Integer.parseInt(input[2]), Integer.parseInt(input[3])))
                            System.out.println("Lift successfully added");
                        else
                            System.out.println("Lift with specified id already exists");
                        break;
                    case "removeLift":
                        if (liftSystem.removeLift(Integer.parseInt(input[1])))
                            System.out.println("Lift successfully removed");
                        else
                            System.out.println("Lift with specified id doesn't exist");
                        break;
                    case "updateLift":
                        if (liftSystem.updateLift(Integer.parseInt(input[1]), Integer.parseInt(input[2]), Integer.parseInt(input[3])))
                            System.out.println("Lift successfully updated");
                        else
                            System.out.println("Lift with specified id doesn't exist");
                        break;
                    case "requestPickup":
                        if (liftSystem.requestPickup(Integer.parseInt(input[1]), Integer.parseInt(input[2])))
                            System.out.println("Pickup successfully requested");
                        else
                            System.out.println("Invalid input data");
                        break;
                    case "requestDestination":
                        if (liftSystem.requestDestination(Integer.parseInt(input[1]), Integer.parseInt(input[2])))
                            System.out.println("Destination successfully requested");
                        else
                            System.out.println("Invalid input data");
                        break;
                    case "step":
                        int n = 1;
                        if (input.length > 1)
                            n = Integer.parseInt(input[1]);
                        for (int i = 0; i < n; i++)
                            liftSystem.step();
                        break;
                    case "status":
                        if (input.length > 1) {
                            Map<Integer, Lift.LiftStatus> status = liftSystem.getStatus();
                            if (status.containsKey(Integer.parseInt(input[1])))
                                System.out.println(liftSystem.getStatus().get(Integer.parseInt(input[1])));
                            else
                                System.out.println("Lift with specified id doesn't exist");
                        }
                        else
                            System.out.print(statusString(liftSystem));
                        break;
                    case "exit":
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid argument");
            } catch (IndexOutOfBoundsException e){
                System.out.println("Invalid input format");
            }
        }

    }
}
