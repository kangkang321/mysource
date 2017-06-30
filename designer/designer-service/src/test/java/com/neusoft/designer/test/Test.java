package com.neusoft.designer.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2ObjectInput;
import com.neusfot.designer.domain.vo.LayoutVO;

public class Test {

    public static void main(String[] args) {

        try {
            // FileOutputStream file = new FileOutputStream("C://out");
            // Hessian2ObjectOutput out = new Hessian2ObjectOutput(file);
            // LayoutVO vo = new LayoutVO();
            // vo.setId("123");
            // vo.setColumns(JSON.parseArray("[{\"field\":\"name\",\"column\":\"\",\"name\":\"姓名\",\"id\":\"094324382B01428C956697FBA90AD4A6\"}]"));
            // out.writeObject(vo);
            // out.flushBuffer();
            // file.flush();
            // file.close();
            // Hessian2ObjectInput in = new Hessian2ObjectInput(new FileInputStream("C:://out"));
            // vo = in.readObject(LayoutVO.class);
            FileInputStream f = new FileInputStream("C://out");
            Hessian2ObjectInput in = new Hessian2ObjectInput(f);
            LayoutVO vo = in.readObject(LayoutVO.class);
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
