package org.b3log.zephyr.command;

/**
 * Created by Zephyr on 2017/2/9.
 */
public enum Command {
    //nama(value)
    COOK_DINNER("做饭"),
    MAKE_BED("铺床"),
    WASH_CLOTHES("洗衣服"),
    WASH_DISHES("洗碗");

    public final String value;
    Command(String value){
        this.value=value;
    }

    @Override
    public String toString() {
        switch(this){
            case COOK_DINNER :
                return "点火，倒油，放菜，翻炒，出锅";
            case MAKE_BED:
                return "叠被子，收衣服，拉床单";
            case WASH_CLOTHES:
                return "插电，放衣服，放洗衣粉，放水，启动";
            case WASH_DISHES:
                return "放水，倒洗洁精，洗一遍，冲一遍";
            default:
                return null;
        }
    }

    public static Command get(String str){
        Command[] cd=Command.class.getEnumConstants();
        for(Command c:cd){
            if(c.value.equals(str))
                return c;
        }
        throw new RuntimeException("WRONG "+Command.class.getSimpleName()+" string value! "+str);
    }
}
