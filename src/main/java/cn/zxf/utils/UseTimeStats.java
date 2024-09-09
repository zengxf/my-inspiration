package cn.zxf.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 简单的用时统计
 * <p/>
 * Created by ZXFeng on 2024/9/9
 */
@Slf4j
public class UseTimeStats {

    /*** 统计总开关。默认禁用，单元测试(或线上用 Arthas)打开 */
    public static boolean ENABLE = false;

    private String mainSign;
    private Long lv1StartTs;
    private Long lv2StartTs;

    /*** 开启一个用时统计 */
    public static UseTimeStats start(String mainSign) {
        UseTimeStats stats = new UseTimeStats();
        stats.mainSign = mainSign;
        stats.lv1StartTs = System.currentTimeMillis();
        stats.lv2StartTs = System.currentTimeMillis();
        return stats;
    }

    /*** 打印 2 级用时并重置 */
    public void outLv2(String lv2Sign) {
        if (!ENABLE) {
            return;
        }
        long use = System.currentTimeMillis() - lv2StartTs;
        log.info("[{} -> {}] (Lv2) 用时：[{}]ms", mainSign, lv2Sign, use);
        lv2StartTs = System.currentTimeMillis();
    }

    /*** 打印 1 级用时并重置 */
    public void outLv1(String lv1Sign) {
        if (!ENABLE) {
            return;
        }
        long use = System.currentTimeMillis() - lv1StartTs;
        log.info("[{} -> {}] (Lv1) 用时：[{}]ms", mainSign, lv1Sign, use);
        lv1StartTs = System.currentTimeMillis();
    }

}
