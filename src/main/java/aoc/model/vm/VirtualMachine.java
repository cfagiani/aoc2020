package aoc.model.vm;

/**
 * Interface that must be implemented by a virtual machine.
 */
public interface VirtualMachine {

    /**
     * Updates the accumulator by the value passed in.
     *
     * @param increment
     */
    void updateAccumulator(int increment);

    /**
     * Returns the current value of the accumulator.
     *
     * @return
     */
    long getAccumulator();

    /**
     * Updates the instruction pointer by the value passed in.
     *
     * @param offset
     */
    void updateInstructionPointer(int offset);
}
