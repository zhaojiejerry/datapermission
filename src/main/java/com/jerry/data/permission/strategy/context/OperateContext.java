package com.lunz.permission.strategy.context;

import com.lunz.permission.strategy.Strategy;
import com.lunz.permission.strategy.rules.*;

import java.util.HashMap;
import java.util.Map;

/**
 * author:lizhaojie
 * 创建日期:2019/10/29-9:27
 */
public class OperateContext   {

    public static Map<String,Strategy>  map = new HashMap<String,Strategy>();

    static {

        init();

    }

    private static void init(){
        map.put("=", new EqualsRule());
        map.put(">",new GreaterThanRule());
        map.put(">=",new GreaterThanEqualsRule());
        map.put("<",new MinorThanRule());
        map.put("<=",new MinorThanEqualsRule());
        map.put("!=",new NotEqualsRule());
        map.put("<>",new NotEqualsRule());
        map.put("between",new BetweenRule());
        map.put("not between", new NotBetweenRule());
        map.put("like",new LikeRule());
        map.put("not like",new NotLikeRule());
        map.put("in",new InRule());
        map.put("not in",new NotInRule());
        map.put("is null",new IsNullRule());
        map.put("is not null",new IsNotNullRule());
    }

    public static Strategy getStrategy(String key) throws Exception {

        String _key = key.toLowerCase();

        if (map.containsKey(_key)) {

            return map.get(key);
        } else {
            throw new Exception("未找到对应规则！" + _key);
        }
    }
}
