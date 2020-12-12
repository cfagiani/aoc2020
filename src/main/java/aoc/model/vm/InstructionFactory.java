package aoc.model.vm;

public class InstructionFactory {

    public static Instruction createInstruction(String input) {
        String[] parts = input.split(" ");
        switch (parts[0]) {
            case NoOpInstruction.CODE:
                if (parts.length == 2) {
                    return new NoOpInstruction(Integer.parseInt(parts[1]));
                } else {
                    return new NoOpInstruction(0);
                }
            case AccumulateInstruction.CODE:
                return new AccumulateInstruction(Integer.parseInt(parts[1]));
            case JumpInstruction.CODE:
                return new JumpInstruction(Integer.parseInt(parts[1]));
            default:
                throw new IllegalArgumentException("Unrecognized instruction: " + input);
        }
    }
}
