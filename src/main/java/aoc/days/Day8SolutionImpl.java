package aoc.days;

import aoc.DaySolution;
import aoc.model.vm.AccumulateInstruction;
import aoc.model.vm.Emulator;
import aoc.model.vm.Instruction;
import aoc.model.vm.InstructionFactory;
import aoc.model.vm.JumpInstruction;
import aoc.model.vm.NoLoopEmulator;
import aoc.model.vm.NoOpInstruction;
import aoc.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This solutions uses a rudimentary virtual machine that can run programs made up an instruction set containing 3
 * instructions (jmp, nop, acc). In part 1, it finds the value in the accumulator right before it enters into an
 * infinite loop. In part 2, it finds the 1 instruction that can be swapped that will let the program run to completion
 * and then outputs the accumulator value after successful termination.
 */
public class Day8SolutionImpl implements DaySolution {

    @Override
    public void part1(String input) {
        Emulator emulator = new NoLoopEmulator();
        emulator.setProgram(buildProgram(input));
        emulator.run();

        System.out.println("Before loop, acc is " + emulator.getAccumulator());
    }

    @Override
    public void part2(String input) {
        List<Instruction> program = buildProgram(input);
        for (int i = 0; i < program.size(); i++) {
            if (program.get(i) instanceof AccumulateInstruction) {
                continue;
            }
            // if we're here, swap the instruction and then try to run the program.
            List<Instruction> instList = new ArrayList<>();
            for (int j = 0; j < program.size(); j++) {
                if (i != j) {
                    instList.add(program.get(j).clone());
                } else {
                    if (program.get(j) instanceof NoOpInstruction) {
                        instList.add(new JumpInstruction(((NoOpInstruction) program.get(j)).getArg()));
                    } else {
                        instList.add(new NoOpInstruction(((JumpInstruction) program.get(j)).getOffset()));
                    }
                }
            }
            Emulator emulator = new NoLoopEmulator();
            emulator.setProgram(instList);
            if (emulator.run()) {
                //if we're here, we got to the end without a loop
                System.out.println("Accumulator has " + emulator.getAccumulator() + " when we swap instruction " + i);
                return;
            }
        }
    }

    /**
     * Builds the program from the input file passed in.
     *
     * @param input
     * @return
     */
    private List<Instruction> buildProgram(String input) {
        List<String> lines = StringUtil.splitOnLines(input);
        List<Instruction> program = new ArrayList<>();
        for (String line : lines) {
            program.add(InstructionFactory.createInstruction(line));
        }
        return program;
    }
}
