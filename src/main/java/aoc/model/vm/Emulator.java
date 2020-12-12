package aoc.model.vm;

import java.util.ArrayList;
import java.util.List;

/**
 * Virtual machine implementation started in Day 8. This VM has a list of instructions and will execute them in order.
 * It maintains an instructionPointer and an accumulator. When Run is called, the program runs to completion.
 */
public class Emulator implements VirtualMachine {

    protected long accumulator;
    protected int instructionPointer;
    protected List<Instruction> instructions;

    public Emulator() {
        this.accumulator = 0;
        this.instructionPointer = 0;
        instructions = new ArrayList<>();
    }

    public void setProgram(List<Instruction> code) {
        this.instructions = code;
    }

    @Override
    public void updateAccumulator(int increment) {
        this.accumulator += increment;
    }

    @Override
    public long getAccumulator() {
        return accumulator;
    }

    @Override
    public void updateInstructionPointer(int offset) {
        this.instructionPointer += offset;
    }

    /**
     * Attempts to run the program loaded into the emulator to completion. If there is an error, this method returns
     * false. If there are no errors and the program runs to completion, it will return true.
     *
     * @return
     */
    public boolean run() {
        while (instructionPointer >= 0 && instructionPointer < instructions.size()) {
            this.instructions.get(this.instructionPointer).execute(this);
        }
        return true;
    }


}
