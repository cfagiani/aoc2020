package aoc.model.vm;

/**
 * Specialization of the Emulator for use in Day 8. It will treat the existence of a loop as an error.
 */
public class NoLoopEmulator extends Emulator {

    /**
     * Runs the program configured in this emulator BUT exits right before executing an instruction for the second time.
     *
     * @return
     */
    public boolean run() {
        while (instructionPointer >= 0 && instructionPointer < instructions.size()) {
            Instruction inst = this.instructions.get(this.instructionPointer);
            if (inst.getExecutionCount() == 1) {
                return false;
            }
            inst.execute(this);
        }
        return true;
    }

}
