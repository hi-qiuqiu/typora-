package classAndObject.demo;

public class ClassStudy {
    String nickname;  // 昵称
    String position;  // 职位
    String city;      // 城市
    String sex;          // 男 | 女

    public void studyCourse() {
        System.out.println("学习课程");
    }
    public void postComment() {
        System.out.println("发表评论");
    }
    public void postArticle() {
        System.out.println("发表手记");
    }

    public static void main(String[] args) {
        // 实例化学生对象
        ClassStudy qiuYa = new ClassStudy();
        ClassStudy panLe = new ClassStudy();

        qiuYa.nickname = "qq";
        qiuYa.city = "长沙";
        qiuYa.sex = "女";

        panLe.nickname = "pp";
        panLe.sex = "男";

        // 调用并打印成员属性
        System.out.println("邱ya昵称：" + qiuYa.nickname);
        System.out.println("邱ya城市：" + qiuYa.city);
        System.out.println("邱ya性别：" + qiuYa.sex);

        System.out.println("潘le昵称：" + panLe.nickname);
        System.out.println("潘le性别：" + panLe.sex);
    }
}
