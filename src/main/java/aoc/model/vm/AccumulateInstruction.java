package aoc.model.vm;

/**
 * This instruction updates the accumulator by the increment passed in.
 */
public class AccumulateInstruction extends Instruction {

    public static final String CODE = "acc";
    private int val;

    public AccumulateInstruction(int arg) {
        val = arg;
        postInstructionIPOffset = 1;
    }

    @Override
    protected void executeInternal(VirtualMachine vm) {
        vm.updateAccumulator(val);
    }

    @Override
    public AccumulateInstruction clone() {
        return new AccumulateInstruction(val);
    }
}
