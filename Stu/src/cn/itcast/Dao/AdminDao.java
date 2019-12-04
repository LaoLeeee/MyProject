package cn.itcast.Dao;

import cn.itcast.utils.JDBCUtils;
import com.alibaba.druid.util.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class AdminDao {

    public AdminDao() {
    }

        //根据用户名和密码做查询
        JdbcTemplate jt = new JdbcTemplate(JDBCUtils.getDataSource());
        //创建一个Admin类
        public Admin findAdminByNameAndPswd(Admin a){
            //书写sql语句

            try {
                String sql = "select * from administrator where username = ? and password = ?";
                System.out.println(a);
                Admin result = jt.queryForObject(sql,new BeanPropertyRowMapper<Admin>(Admin.class),a.getUsername(),a.getPassword());
                System.out.println("33333333333333");
                return result;
            } catch (DataAccessException e) {
                return null;
            }

        }


}
