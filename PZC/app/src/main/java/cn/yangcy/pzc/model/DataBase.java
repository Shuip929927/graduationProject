package cn.yangcy.pzc.model;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.department.DepartmentDao;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.model.imformation.InformationDao;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserDao;

//singleton 单例
@Database(entities = {User.class, Department.class, Information.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    private static final String DB_NAME = "DB_PZC";
    private static final String TAG = "DB";
    private static DataBase INSTANCE;

    public static synchronized DataBase getDataBase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataBase.class, DB_NAME)
                    .allowMainThreadQueries().addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.i(TAG, "onCreate: DB");
                            db.execSQL("insert into tb_department(department) values('信息工程系')");
                            db.execSQL("insert into tb_department(department) values('计算机工程系')");
                            db.execSQL("insert into tb_department(department) values('电气工程系')");
                            db.execSQL("insert into tb_department(department) values('化学工程系')");
                            db.execSQL("insert into tb_department(department) values('土木工程系')");
                            db.execSQL("insert into tb_department(department) values('建筑系')");
                            db.execSQL("insert into tb_department(department) values('材料工程系')");
                            db.execSQL("insert into tb_department(department) values('环境资源工程系')");
                            db.execSQL("insert into tb_department(department) values('食品与生物工程系')");
                            db.execSQL("insert into tb_department(department) values('传播与艺术系')");
                            db.execSQL("insert into tb_department(department) values('经济管理系')");
                            db.execSQL("insert into tb_department(department) values('人文艺术系')");
                            db.execSQL("insert into tb_department(department) values('外国语系')");

                            db.execSQL("insert into tb_user values('test','test','test'" +
                                    ",'10000000000','计算机工程系','软件工程','1',6)");

                            db.execSQL("insert into tb_information(title,type,author,createOn,content) " +
                                    "values('我院学子在第九届“新华三杯”全国大学生数字技术大赛全国总决赛中获得佳绩','喜讯','福州大学至诚学院','2019-12-27 00:00:00'," +
                                    "'日前，第九届“新华三杯”全国大学生数字技术大赛全国总决赛于2019年12月13日至15日在浙江经贸职业" +
                                    "技术学院举行。我院参赛学子成绩优异，均获得名次，其中，二等奖1名，三等奖1名(指导老师：计算机工程系计算机" +
                                    "业教研室黄巧云)。 大赛点燃校园，技术改变人生。“新华三杯”全国大学生数字技术大赛由紫光旗下的新华三集团和全国高等院" +
                                    "校计算机基础教育研究会联合主办，旨在推动学生、院校、企业三方互动，“以赛促学习、以赛促教学、以赛促就业”，积极探索高科技技" +
                                    "术人才培养思路，丰富多元化人才培养体系，建立良性的就业生态圈。2010年至今，新华三杯大赛已成功举办9届，累计3000多所院校的2300" +
                                    "0多人参与比赛，万余名选手脱颖而出，成功就职于新华三集团及其人才联盟企业。')");

                            db.execSQL("insert into tb_information(title,type,author,createOn,content) " +
                                    "values('我院召开元旦期末和寒假期间安全稳定工作会议','通知','福州大学至诚学院','2019-12-24 00:00:00','" +
                                    "12月23日上午，我院在行政楼会议室召开元旦期末及寒假安全稳定工作会议。院领导及各系主任" +
                                    "、各部处负责人参加会议，刘松青常务副书记主持会议。 会议就元旦期末及寒假期间学院安全稳定工作进行了部" +
                                    "署，提出了要求：一要充分认识高校安全稳定工作的重要性，进一步提高政治站位、增强政治意识，提升做好安全稳定工作的意识" +
                                    "自觉和行动自觉；二要加强安全教育与宣传，进一步增强师生安全意识，提高师生防范和应对突发事件的能力；三要抓住工作重点，认真" +
                                    "开展安全大排查，全面摸清学院安全隐患和薄弱环节，落实整改责任和整改措施，彻底治理各类安全隐患，坚决防范安全事故的发生；四是加" +
                                    "强阵地管理，紧紧围绕上级要求，结合本单位实际，进一步夯实安全维稳责任，强化安全措施落实，筑牢校园安全稳定防护网；五要强化校园周" +
                                    "边综合治理，营造整洁、文明、有序的校园周边环境；六是落实假期值班值守制度，切实做好安全防范工作，确保学院的安定稳定。')");

                            db.execSQL("insert into tb_information(title,type,author,createOn,content) " +
                                    "values('我院召开党员领导干部述职考核民主测评会议','通知','福州大学至诚学院','2020-01-02 08:00:00'," +
                                    "'按照《福州大学党委关于做好2019年度处级、校聘、科级干部考核工作的通知》要求，12月31日上午，我院在行政办公楼会议" +
                                    "室召开党员领导干部述职考核民主测评大会。校党委组织部指派林林、吕蓉蓉2位老师前来指导，学院院领导、党委委员、各系主" +
                                    "任、机关各部处负责人、各党总支书记、教工党支部书记及校级以上党代会代表、两代会代表参加会议。会议由院党委刘松青常务" +
                                    "副书记主持。\n会上，刘松青常务副书记代表院领导班子总结了2019年学院各项工作。2019年，至诚学院在福州大学和学院董事会" +
                                    "的正确领导下，坚持全面从严治党，坚持立德树人，深化综合改革，着力依法治校，加强内涵建设，推进高水平应用型大学建设，各" +
                                    "项事业取得新发展，获批了全国教育系统先进集体和全国党建工作样板支部，学院的知名度与美誉度持续提升。一是坚持全面从严治党" +
                                    "，党建科学化水平不断提升；二是强化办学顶层设计，高水平应用型大学建设全面推进；三是加强办学内涵建设，学院综合实力持续提升" +
                                    "；四是营造和谐育人生态，师生的获得感、幸福感持续提升。\n" +
                                    "随后，刘松青常务副书记、雷晓明副书记、翁德顺纪委书记依次围绕德能勤绩廉五个方面，总结自己一年来的履职情况，向全体与会人员作了公开述职。\n" +
                                    "公开述职结束后，所有参会人员对学院领导班子和述职的3位干部进行了民主测评。')");

                            db.execSQL("insert into tb_information(title,type,author,createOn,content) " +
                                    "values('至诚学院代表队在2019年福建省高校“校长杯”乒乓球赛中夺得佳绩','喜讯','福州大学至诚学院','2019-12-24 09:00:12'," +
                                    "'12月21日-22日，由省教育厅主办的2019年福建省高校“校长杯”乒乓球选拔赛在福建体育职业技术学院举行，" +
                                    "福建省教育厅和全省各高校组织队伍参与角逐。我院组队参加了团体赛和个人单项比赛，经过激烈的切磋较量，" +
                                    "取得了优异成绩：至诚学院代表队夺得团体项目二等奖（第五名）；林行副院长参加了男子单打项目，夺得了男子单打第六名。\n" +
                                    "    附：我院代表队名单\n" +
                                    "    教练：邱威  领队：苏童\n" +
                                    "    队员: 雷晓明、林行、刘德荣')");
                            db.execSQL("insert into tb_information(title,type,author,createOn,content) " +
                                    "values('我院获福州大学2019年新闻宣传工作及十佳校园微信平台表彰','喜讯','福州大学至诚学院','2019-12-18 09:00:12'," +
                                    "'日前，福州大学党委公布了2019年新闻宣传工作先进集体、先进个人及十佳校园微信平台表彰结果。经线上投票与专家评审，" +
                                    "我院团委学生会官方微信平台“微小至”连续第四年被评为“福州大学十佳校园微信平台”。" +
                                    "同时，院党委办公室刘志勇老师在表彰中被评为2019年度福州大学新闻宣传工作先进个人。')");
                        }

                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            Log.i(TAG, "onOpen: DB");
                        }
                    }).build();
        }
        return INSTANCE;
    }


    public abstract UserDao getUserDao();

    public abstract DepartmentDao getDepartmentDao();

    public abstract InformationDao getInformationDao();
}
