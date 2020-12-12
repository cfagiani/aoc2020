package aoc.model.vm;

/**
 * Base class for any instruction that can be run by our Virtual Machine.
 */
public abstract class Instruction {
    private int executionCount = 0;
    protected int postInstructionIPOffset = 0;


    /**
     * Counts the invocation of the instruction and dispatches to the executeInternal method which is implemented by
     * subclasses.
     *
     * @param vm
     */
    public void execute(VirtualMachine vm) {
        executionCount++;
        executeInternal(vm);
        vm.updateInstructionPointer(postInstructionIPOffset);
    }

    /**
     * Abstract method that must be implemented by subclasses. This method should perform the actual work for the
     * instruction.
     *
     * @param vm
     */
    protected abstract void executeInternal(VirtualMachine vm);


    public int getExecutionCount() {
        return executionCount;
    }

    /**
     * Copies this instruction.
     *
     * @return
     */
    public abstract Instruction clone();
}
