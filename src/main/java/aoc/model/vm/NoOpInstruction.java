package aoc.model.vm;

/**
 * No op instructions do nothing.
 */
public class NoOpInstruction extends Instruction {

    public static final String CODE = "nop";

    private int arg;

    public NoOpInstruction(Integer arg) {
        postInstructionIPOffset = 1;
        this.arg = arg;
    }

    @Override
    protected void executeInternal(VirtualMachine vm) {
        return;
    }

    @Override
    public NoOpInstruction clone() {
        return new NoOpInstruction(arg);
    }

    public int getArg() {
        return arg;
    }
}
