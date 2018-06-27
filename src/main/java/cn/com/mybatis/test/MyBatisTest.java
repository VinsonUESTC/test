package cn.com.mybatis.test;

import cn.com.mybatis.datasource.DataConnection;
import cn.com.mybatis.po.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyBatisTest {
    public DataConnection dataConn = new DataConnection();
    @Test
    public void TestSelect() throws IOException{
        SqlSession sqlSession = dataConn.getSqlSession();
        //sqlSession.selectOne 最终结果与映射文件中匹配的 resultType()
        User user = sqlSession.selectOne("test.findUserById",1);
        System.out.println("姓名:"+user.getUsername());
        System.out.println("性别:"+user.getGender());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        System.out.println("生日:"+user.getBirthday());
        System.out.println("所在地:"+user.getProvince());
        sqlSession.close();
    }

    @Test
    public void TestFuzzySearch() throws IOException {
        SqlSession sqlSession = dataConn.getSqlSession();
        List<User> userList = sqlSession.selectList("test.findUserByUsername","丽");
        for (User user : userList){
            System.out.println("姓名:"+user.getUsername());
            System.out.println("性别:"+user.getGender());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            System.out.println("生日:"+user.getBirthday());
            System.out.println("所在地:"+user.getProvince());
        }
        sqlSession.close();
    }

    @Test
    public void TestInsert() throws IOException, ParseException {
        SqlSession sqlSession = dataConn.getSqlSession();
        User user = new User();
        user.setUsername("孙佳佳");
        user.setGender("男");
        user.setPassword("5555");
        user.setEmail("5555@126.com");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        user.setBirthday(sdf.parse("1991-02-16"));
        user.setProvince("湖北省");
        user.setCity("武汉市");
        sqlSession.insert("test.insertUser",user);
        sqlSession.commit();
        sqlSession.close();
    }
}
