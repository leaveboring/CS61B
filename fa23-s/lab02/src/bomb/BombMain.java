package bomb;

import common.IntList;

public class BombMain {
    public static void main(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct inputs (passwords) to each phase using debugging techniques
        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0("39291226");
        }
        int[] answer = new int[]{0, 9, 3, 0, 8};
        IntList password = IntList.of(answer);
        if (phase >= 1) {
            b.phase1(password); // Figure this out too
        }
        StringBuilder sb = new StringBuilder();
        sb.append("i ".repeat(1337));
        sb.append("-81201430");
        if (phase >= 2) {
            b.phase2(sb.toString());
        }
    }
}
