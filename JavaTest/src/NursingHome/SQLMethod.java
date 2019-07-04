package NursingHome;

import NursingHome.dataclass.Administrator;
import NursingHome.dataclass.Doctor;
import NursingHome.dataclass.DoorBoy;
import NursingHome.dataclass.Worker;

import java.sql.*;

import static NursingHome.ControllerUtils.*;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class SQLMethod {
    public static String MYSQL_URL = "jdbc:mysql://106.15.187.231";
    public static String MYSQL_USER = "quest";
    public static String MYSQL_PASSWORD = "QWEasd123!@#";

    /**
     * 随机生成一个新密码并赋给新员工
     *
     * @param object 员工对象
     */
    public static void newPassword(Object object) {
        Connection conn;
        Statement stmt;
        String tempPassword = randomPassword();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation()==Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql = "";
                if (object.getClass().getName().equals("NursingHome.dataclass.Doctor")) {
                    Doctor doctor = ((Doctor) object);
                    showAlert("[提示]该医生的密码为：" + tempPassword);
                    sql = "INSERT INTO NursingHome.manager VALUES ('" + doctor.getId() + "',2,'" + md5(tempPassword) + "');";
                } else if (object.getClass().getName().equals("NursingHome.dataclass.DoorBoy")) {
                    DoorBoy doorBoy = ((DoorBoy) object);
                    showAlert("[提示]该前台人员的密码为：" + tempPassword);
                    sql = "INSERT INTO NursingHome.manager VALUES ('" + doorBoy.getId() + "',3,'" + md5(tempPassword) + "');";
                } else if (object.getClass().getName().equals("NursingHome.dataclass.Administrator")) {
                    Administrator admin = ((Administrator) object);
                    showAlert("[提示]该行政人员的密码为：" + tempPassword);
                    if (admin.getPosition().equals("主管")) {
                        sql = "INSERT INTO NursingHome.manager VALUES ('" + admin.getId() + "',0,'" + md5(tempPassword) + "');";
                    } else {
                        sql = "INSERT INTO NursingHome.manager VALUES ('" + admin.getId() + "',1,'" + md5(tempPassword) + "');";
                    }
                }
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * 更改客户位置
     *
     * @param flag     表示护工增加或者减少任务
     * @param workerId 护工编号
     * @param roomId   房间编号
     */
    public static void changeWorkerPos(boolean flag, String workerId, String roomId) {
        // TODO flag为true代表增加护工任务
        Connection conn;
        Statement stmt;
        double room_idDouble = Double.valueOf(roomId.substring(1));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation()==Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql, sql1;
                if (flag) {
                    sql = "UPDATE NursingHome.worker SET worker_vispos = (worker_vispos*worker_customernumber+" + room_idDouble + ")/(worker_customernumber+1) WHERE worker_id='" + workerId + "';";
                    sql1 = "UPDATE NursingHome.worker SET worker_customernumber = worker_customernumber+1 WHERE worker_id='" + workerId + "'";
                } else {
                    // TODO 为护工减少该任务
                    String sql2 = "SELECT worker_customernumber-1 FROM NursingHome.worker WHERE worker_id = '" + workerId + "'";
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql2);
                    int N = 0;
                    if (rs.first()) {
                        N = rs.getInt(1);
                    }
                    if (N == 0) {
                        sql = "UPDATE NursingHome.worker SET worker_vispos=0 WHERE worker_id='" + workerId + "';";
                        sql1 = "UPDATE NursingHome.worker SET worker_customernumber=worker_customernumber-1 WHERE worker_id='" + workerId + "'";
                    } else {
                        sql = "UPDATE NursingHome.worker SET worker_vispos=(worker_vispos*worker_customernumber-" + room_idDouble + ")/(worker_customernumber-1) WHERE worker_id='" + workerId + "';";
                        sql1 = "UPDATE NursingHome.worker SET worker_customernumber=worker_customernumber-1 WHERE worker_id='" + workerId + "';";
                    }
                }
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.executeUpdate(sql1);
                stmt.close();
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向数据库中插入一个新员工
     *
     * @param object 员工对象
     */
    public static void newPeople(Object object) {
        // TODO 在数据库中新增信息,在历史员工表中也要添加信息
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation()==Connection.TRANSACTION_REPEATABLE_READ) {
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql = "";
                String sql1 = "";
                if (object.getClass().getName().equals("NursingHome.dataclass.Worker")) {
                    Worker worker = ((Worker) object);
                    sql1 = "INSERT INTO NursingHome.historical_staff VALUES ('" + worker.getId() + "','1')";
                    sql = "INSERT INTO NursingHome.worker VALUES " + worker.getWorkerInfo();
                } else if (object.getClass().getName().equals("NursingHome.dataclass.Doctor")) {
                    Doctor doctor = ((Doctor) object);
                    sql1 = "INSERT INTO NursingHome.historical_staff VALUES ('" + doctor.getId() + "','1')";
                    sql = "INSERT INTO NursingHome.doctor VALUES " + doctor.getDoctorInfo();

                } else if (object.getClass().getName().equals("NursingHome.dataclass.DoorBoy")) {
                    DoorBoy doorBoy = ((DoorBoy) object);
                    sql1 = "INSERT INTO NursingHome.historical_staff VALUES ('" + doorBoy.getId() + "','1')";
                    sql = "INSERT INTO NursingHome.doorboy VALUES " + doorBoy.getDoorBoyInfo();

                } else if (object.getClass().getName().equals("NursingHome.dataclass.Administrator")) {
                    Administrator admin = ((Administrator) object);
                    sql1 = "INSERT INTO NursingHome.historical_staff VALUES ('" + admin.getId() + "','1')";
                    sql = "INSERT INTO NursingHome.administrator VALUES " + admin.getAdminInfo();
                }
                stmt = conn.createStatement();
                stmt.executeUpdate(sql1);
                stmt.executeUpdate(sql);
                stmt.close();
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取新的记录、员工、客户id号
     *
     * @param ch 要获取的记录号类型字符
     * @return 返回新的id号
     */
    public static String generateId(char ch) {
        String id = "";
        Connection conn;
        Statement stmt;
        int N = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
            if (conn.getTransactionIsolation()==Connection.TRANSACTION_REPEATABLE_READ){
                conn.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                String sql;
                if (ch == 'C') {
                    sql = "SELECT count(*) FROM NursingHome.historical_customer;";
                } else if (ch == 'R') {
                    sql = "SELECT count(*) FROM NursingHome.record;";
                } else {
                    sql = "SELECT count(*) FROM NursingHome.historical_staff WHERE staff_id LIKE '" + ch + "%';";
                }
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    N = rs.getInt(1);
                }
                rs.close();
                stmt.close();
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        id = String.valueOf(ch) + (N + 1);
        return id;
    }
}
