package aoc.model.vm;

/**
 * Jump instructions updates the instruction pointer by the increment passed in.
 */
public class JumpInstruction extends Instruction {
    public static final String CODE = "jmp";

    private int offset;

    public JumpInstruction(int offset) {
        this.offset = offset;
    }

    @Override
    protected void executeInternal(VirtualMachine vm) {
        vm.updateInstructionPointer(offset);
    }

    @Override
    public JumpInstruction clone() {
        return new JumpInstruction(offset);
    }

    public int getOffset() {
        return offset;
    }
}
