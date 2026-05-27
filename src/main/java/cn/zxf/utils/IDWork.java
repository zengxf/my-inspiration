package cn.zxf.utils;

public final class IDWork {

    private static final Sequence worker = new Sequence();

    private IDWork() {
    }

    public static long gen() {
        return worker.nextId();
    }

    public static String gen(String prefix) {
        long id = worker.nextId();
        java.util.Random rd = new java.util.Random();
        int x = rd.nextInt(9);
        StringBuffer buf = new StringBuffer(prefix).append(id);
        buf.append(x);
        return buf.toString();
    }

}
