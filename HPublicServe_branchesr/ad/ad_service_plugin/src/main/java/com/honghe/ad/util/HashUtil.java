package com.honghe.ad.util;

import com.honghe.ad.campus.dao.CampusDao;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhanghongbin on 2016/9/8.
 */
public final class HashUtil {


    private HashUtil() {
    }

    public static int toHash(String key) {
        int arraySize = 11113;          //数组大小一般取质数
        int hashCode = 0;
        for (int i = 0; i < key.length(); i++) {        //从字符串的左边开始计算
            int letterValue = key.charAt(i) - 96;//将获取到的字符串转换成数字，比如a的码值是97，则97-96=1 就代表a的值，同理b=2；
            hashCode = ((hashCode << 5) + letterValue) % arraySize;//防止编码溢出，对每步结果都进行取模运算
        }
        return hashCode;
    }
    /**
     *编号格式为一位字母+七位数字的格式 例：B0000123
     *字母表示目录等级，A为二级目录，B为三级目录 以此类推
     *数字表示当前等级目录编号（根据条数递增）
     *先判断需要添加的pid是否为1（根目录）
     *若上级目录为根目录直接添加id等级为A
     *若上级目录为其他目录 直接将此数据添加为下一级目录 例：pid= 1 当前id=B0000000X
     *确定等级后取出当前数据库中编号最大的数值
     *若有人恶意输入编号9999999 那么就用9999999取模后 加当前List的个数后 循环+1查看是否有空位
     * @param pId
     * @return
     */
    public static String calculateId(String pId){
        String id = "";
        char level = 'A';
        if("1".equals(pId)){
            pId="A";
        }
        level = (char) (pId.toCharArray()[0]+1);
        List all = CampusDao.INSTANCE.findByWord(level);

        DecimalFormat df=new DecimalFormat("0000000");
        if (all==null||all.size()==0){
            id=level+df.format(1);
        }else {
            Map map = (Map)all.get(0);
            String last = map.get("campusId").toString();
            int max = Integer.valueOf(last.substring(1, last.length()));
            //此处判断若已经含有9999999的id的处理方法
            max +=1;
            if (max>9999999){
                //若数值超出最大范围 那么取模后+原有数据的数量后进行重复判断
                int num = max/9999999+all.size();
                Set set = new HashSet();
                //将已有数据保存 用于重复判断
                for (int i = 0;i<all.size();i++){
                    Map used = (Map)all.get(i);
                    set.add(used.get("campusId"));
                }
                //记录当前的集合大小  用于后面判断
                int oldSize = set.size();
                id=level+df.format(num);
                set.add(id);
                //若set集合大小不变则说明该值和已有数值重复 若变大 则证明当前值不重复 可以保存
                while (set.size()==oldSize){
                    num+=1;
                    id=level+df.format(num);
                    set.add(id);
                }
            }else {
                id = level+df.format(max);
            }
        }
            return id;
    }
//    private static int chineseNumber2Int(String chineseNumber){
//        int result = 0;
//        int temp = 1;//存放一个单位的数字如：十万
//        int count = 0;//判断是否有chArr
//        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
//        char[] chArr = new char[]{'十','百','千','万','亿'};
//        for (int i = 0; i < chineseNumber.length(); i++) {
//            boolean b = true;//判断是否是chArr
//            char c = chineseNumber.charAt(i);
//            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
//                if (c == cnArr[j]) {
//                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
//                        result += temp;
//                        temp = 1;
//                        count = 0;
//                    }
//                    // 下标+1，就是对应的值
//                    temp = j + 1;
//                    b = false;
//                    break;
//                }
//            }
//            if(b){//单位{'十','百','千','万','亿'}
//                for (int j = 0; j < chArr.length; j++) {
//                    if (c == chArr[j]) {
//                        switch (j) {
//                            case 0:
//                                temp *= 10;
//                                break;
//                            case 1:
//                                temp *= 100;
//                                break;
//                            case 2:
//                                temp *= 1000;
//                                break;
//                            case 3:
//                                temp *= 10000;
//                                break;
//                            case 4:
//                                temp *= 100000000;
//                                break;
//                            default:
//                                break;
//                        }
//                        count++;
//                    }
//                }
//            }
//            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
//                result += temp;
//                System.out.println(temp);
//            }
//        }
//        return result;
//    }
}
