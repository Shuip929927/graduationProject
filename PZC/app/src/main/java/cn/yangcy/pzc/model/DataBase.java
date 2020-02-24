package cn.yangcy.pzc.model;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import cn.yangcy.pzc.model.activities.Activities;
import cn.yangcy.pzc.model.activities.ActivitiesDao;
import cn.yangcy.pzc.model.department.Department;
import cn.yangcy.pzc.model.department.DepartmentDao;
import cn.yangcy.pzc.model.imformation.Information;
import cn.yangcy.pzc.model.imformation.InformationDao;
import cn.yangcy.pzc.model.organization.Organization;
import cn.yangcy.pzc.model.organization.OrganizationDao;
import cn.yangcy.pzc.model.user.User;
import cn.yangcy.pzc.model.user.UserDao;

//singleton 单例
@Database(entities = {User.class, Department.class, Information.class, Organization.class, Activities.class}, version = 1, exportSchema = false)
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
                            db.execSQL("insert into tb_department(department_name) values('信息工程系')");
                            db.execSQL("insert into tb_department(department_name) values('计算机工程系')");
                            db.execSQL("insert into tb_department(department_name) values('电气工程系')");
                            db.execSQL("insert into tb_department(department_name) values('化学工程系')");
                            db.execSQL("insert into tb_department(department_name) values('土木工程系')");
                            db.execSQL("insert into tb_department(department_name) values('建筑系')");
                            db.execSQL("insert into tb_department(department_name) values('材料工程系')");
                            db.execSQL("insert into tb_department(department_name) values('环境资源工程系')");
                            db.execSQL("insert into tb_department(department_name) values('食品与生物工程系')");
                            db.execSQL("insert into tb_department(department_name) values('传播与艺术系')");
                            db.execSQL("insert into tb_department(department_name) values('经济管理系')");
                            db.execSQL("insert into tb_department(department_name) values('人文艺术系')");
                            db.execSQL("insert into tb_department(department_name) values('外国语系')");


                            db.execSQL("insert into tb_user values('test','test','test'" +
                                    ",'10000000000','计算机工程系','软件工程','1',6)");


                            db.execSQL("insert into tb_information(title,type,author,createOn,content) values" +
                                    "('我院学子在第九届“新华三杯”全国大学生数字技术大赛全国总决赛中获得佳绩'," +
                                    "'喜讯'," +
                                    "'福州大学至诚学院'," +
                                    "'2019-12-27 00:00:00'," +
                                    "'日前，第九届“新华三杯”全国大学生数字技术大赛全国总决赛于2019年12月13日至15日在浙江经贸职业" +
                                    "技术学院举行。我院参赛学子成绩优异，均获得名次，其中，二等奖1名，三等奖1名(指导老师：计算机工程系计算机" +
                                    "业教研室黄巧云)。 大赛点燃校园，技术改变人生。“新华三杯”全国大学生数字技术大赛由紫光旗下的新华三集团和全国高等院" +
                                    "校计算机基础教育研究会联合主办，旨在推动学生、院校、企业三方互动，“以赛促学习、以赛促教学、以赛促就业”，积极探索高科技技" +
                                    "术人才培养思路，丰富多元化人才培养体系，建立良性的就业生态圈。2010年至今，新华三杯大赛已成功举办9届，累计3000多所院校的2300" +
                                    "0多人参与比赛，万余名选手脱颖而出，成功就职于新华三集团及其人才联盟企业。')");

                            db.execSQL("insert into tb_information(title,type,author,createOn,content) values" +
                                    "('我院召开元旦期末和寒假期间安全稳定工作会议'," +
                                    "'通知'," +
                                    "'福州大学至诚学院'," +
                                    "'2019-12-24 00:00:00','" +
                                    "12月23日上午，我院在行政楼会议室召开元旦期末及寒假安全稳定工作会议。院领导及各系主任" +
                                    "、各部处负责人参加会议，刘松青常务副书记主持会议。 会议就元旦期末及寒假期间学院安全稳定工作进行了部" +
                                    "署，提出了要求：一要充分认识高校安全稳定工作的重要性，进一步提高政治站位、增强政治意识，提升做好安全稳定工作的意识" +
                                    "自觉和行动自觉；二要加强安全教育与宣传，进一步增强师生安全意识，提高师生防范和应对突发事件的能力；三要抓住工作重点，认真" +
                                    "开展安全大排查，全面摸清学院安全隐患和薄弱环节，落实整改责任和整改措施，彻底治理各类安全隐患，坚决防范安全事故的发生；四是加" +
                                    "强阵地管理，紧紧围绕上级要求，结合本单位实际，进一步夯实安全维稳责任，强化安全措施落实，筑牢校园安全稳定防护网；五要强化校园周" +
                                    "边综合治理，营造整洁、文明、有序的校园周边环境；六是落实假期值班值守制度，切实做好安全防范工作，确保学院的安定稳定。')");

                            db.execSQL("insert into tb_information(title,type,author,createOn,content) values" +
                                    "('我院召开党员领导干部述职考核民主测评会议'," +
                                    "'通知'," +
                                    "'福州大学至诚学院'," +
                                    "'2020-01-02 08:00:00'," +
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

                            db.execSQL("insert into tb_information(title,type,author,createOn,content) values" +
                                    "('至诚学院代表队在2019年福建省高校“校长杯”乒乓球赛中夺得佳绩'," +
                                    "'喜讯'," +
                                    "'福州大学至诚学院'," +
                                    "'2019-12-24 09:00:12'," +
                                    "'12月21日-22日，由省教育厅主办的2019年福建省高校“校长杯”乒乓球选拔赛在福建体育职业技术学院举行，" +
                                    "福建省教育厅和全省各高校组织队伍参与角逐。我院组队参加了团体赛和个人单项比赛，经过激烈的切磋较量，" +
                                    "取得了优异成绩：至诚学院代表队夺得团体项目二等奖（第五名）；林行副院长参加了男子单打项目，夺得了男子单打第六名。\n" +
                                    "    附：我院代表队名单\n" +
                                    "    教练：邱威  领队：苏童\n" +
                                    "    队员: 雷晓明、林行、刘德荣')");
                            db.execSQL("insert into tb_information(title,type,author,createOn,content) values" +
                                    "('我院获福州大学2019年新闻宣传工作及十佳校园微信平台表彰'," +
                                    "'喜讯'," +
                                    "'福州大学至诚学院'," +
                                    "'2019-12-18 09:00:12'," +
                                    "'日前，福州大学党委公布了2019年新闻宣传工作先进集体、先进个人及十佳校园微信平台表彰结果。经线上投票与专家评审，" +
                                    "我院团委学生会官方微信平台“微小至”连续第四年被评为“福州大学十佳校园微信平台”。" +
                                    "同时，院党委办公室刘志勇老师在表彰中被评为2019年度福州大学新闻宣传工作先进个人。')");


                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('综合办公室'," +
                                    "'综合办公室主任'," +
                                    "'综合办公室(两委办)是学生会的中枢部门，维持着学生会的正常运行，" +
                                    "可以说是学生会最重要的部门之一。我们负责主席团和各部门之间的联系，还着手会议记录和对各部门文件汇总建档，" +
                                    "负责信息传递和会议通知。在院活动中我们负责整个学生会大部分物资的管理；负责学院绝大部分晚会，大会的控音。')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('团委组织部'," +
                                    "'组织部部长'," +
                                    "'夏天的风和冬天的雪，都是与你有关的浪漫征途，春天的花和秋天的叶，都是与你有关的人间百态。\n" +
                                    "\n"+
                                    "这里是院团委组织部，负责研究和指导学院各团支部建设和推优工作，" +
                                    "研究和制定团委的组织建设方面的规章制度，并负责团组织建设的日常管理工作，" +
                                    "负责院两委团内干部的管理工作。风花雪叶，盼你前来。')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('社团联合会'," +
                                    "'社团联合会主席'," +
                                    "'社团联合会简称社联，是在院团委的指导下成立的管理社团、服务社团发展的学生会组织。\n" +
                                    "\n" +
                                    "社联下设办公室、策划部和宣传部等部门，作为一个“非常正经”的学生部门，" +
                                    "我们以“服务、引导、监管、管理”为宗旨，协助院团委管理督促众多社团，" +
                                    "简单来说，社联掌握着各大社团的第一手资料，致力开展丰富多样的社团活动，是实在的活力担当！')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('倾听至诚广播电台'," +
                                    "'电台台长'," +
                                    "'倾听至诚广播电台是学院里学生会组织之一，在这里不仅能够提升自己，还能遇见许多优秀的伙伴。\n" +
                                    "\n" +
                                    "电台下设播音部和编辑（策划）部，播音成员负责念稿，用声音传递心声；" +
                                    "编辑（策划）成员负责稿件撰写、节目剪辑和每周推送，用文字打动听众；" +
                                    "每周一到周四的晚上会安排有趣的电台值班；每周还会有清晨练声。\n" +
                                    "\n" +
                                    "即使电台栏目众多，你也可以发挥自己的聪明才智，" +
                                    "在学校及台长的允许下开设一档自己的节目专栏，丰富电台节目。" +
                                    "大学生活的第一笔色彩，从加入电台开始······')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('青年志愿者协会'," +
                                    "'青年志愿者协会会长'," +
                                    "'青年志愿者协会秉持“奉献，友爱，互助，团结”精神，组织各种形式的校内校外志愿者活动，丰富你的课外生活。\n" +
                                    "\n" +
                                    "下设四个部门：文宣部：负责活动的前后期宣传。办公室：负责办公室及活动物资的准备。" +
                                    "项目部：负责活动策划及开展。注册认证部：负责材料管理。亲人虽远，青协很近。" +
                                    "奉献爱心一起来，青协有你更精彩！')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('红十字会'," +
                                    "'红十字会会长'," +
                                    "'在红会能学到与各专业通用的知识和技能;我们还有许多常规活动，" +
                                    "既能让大家的知识技能与社会实践相结合，还能进行一定的知识拓展;" +
                                    "除此之外，我们红会还与外校的同学-起参加各种各样有趣的活动。\n" +
                                    "\n" +
                                    "如果你想提升自己应对突发事件和意外伤害事故的自救互救能力，那就加入我们吧。" +
                                    "在这里你可以学到很多以前没有机会接触的技能，例如:CPR (心肺复苏)以及一些包扎救护的方式，" +
                                    "牢固结绳的方法，还有一-些药品、食品安全的知识普及。\n" +
                                    "\n" +
                                    "此外我们还会定期进行手语培训，会有老师对我们进行教学，让我们更好的了解、学习手语，" +
                                    "而且会有不同学校学生前来，与我们共同学习。我们也会外出参加各式各样的志愿者活动，" +
                                    "在帮助他人的同时提升自己的能力。当然在红会你也会认识很多新朋友，体会到家一样的温暖。')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('科技实践部'," +
                                    "'科技实践部部长'," +
                                    "'我行过许多地方的桥，看过许多次的云，喝过许多种的酒，唯独正当最好的年龄,缺少一个你。\n" +
                                    "\n" +
                                    "你热爱活动的策划与执行，想要Get书籍汇编技能吗？你喜欢摄影和海报制作," +
                                    "试图迅速化身摄影达人、PS大神吗？你愿意管理创业团队，尝试和行业大佬一起举办创业沙龙活动吗？\n" +
                                    "\n" +
                                    "恰好，科技实践部在，给，愿意相逢的你 一个机会，期待与你的相遇。')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('权益部'," +
                                    "'权益部部长'," +
                                    "'权益部是以为全校学生服务为准则，架起学生与学院、学校之间沟通的桥梁 ，" +
                                    "更是为学生谋求更多的利益窗口，是解决学生与学校冲突的缓和区，是权益的捍卫者！\n" +
                                    "\n" +
                                    "那么......我们到底干些啥？安全周、光盘月、三七女生节、消费者权益日、提案大赛、物资物料借用等等，" +
                                    " 都是小权权负责！ \"哪里有不平，哪里就有我” 正义的小权权欢迎你!')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('新媒体中心'," +
                                    "'新媒体中心主任'," +
                                    "'新媒体中心是一个专注于新媒体运营，充满激情与创意碰撞的地方。下设办公室、运营部、技术部" +
                                    "和小至工作室。\n" +
                                    "\n" +
                                    "我们有最实用的干货课程：不管是ps、ai、ae、lr、dw还是秀米、135……无论你是想要成为策划大佬，" +
                                    "微博红人，还是文案编辑，摄影达人，技术大神……通通都可以在这里找到属于你的一席之地。\n" +
                                    "\n" +
                                    "同时，你还可以近距离接触到学院的吉祥物“小至”，为它设计生产一系列的周边。" +
                                    "这里的人有能力，有才华，有梦想，更有追求。只要你够有趣，懂坚持，新媒体中心期待每一个你。')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('青年通讯社'," +
                                    "'青年通讯社社长'," +
                                    "'青年通讯社下设办公室、新闻采编中心、信息部和视频编辑部，" +
                                    "我们致力于培养一支从策划、管理到采编、出版无所不能的高素质、高热情的校媒团队。" +
                                    "我们专注于传递学院的最新动态。\n" +
                                    "\n" +
                                    "我们以大学生的视角，关注透视身边的各种焦点。如果你想了解团学风采，" +
                                    "如果你想成为校媒人才，我们有纸笔，有相机，也有情怀，我是青通社，我们等你来PICK！')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('文艺部'," +
                                    "'文艺部部长'," +
                                    "'我们是乏味生活的艺术家，活泼好动是我们的特点，缜密创新是我们的追求，" +
                                    "团结和睦是我们的风尚。每一场绚烂夺目的演出，每一次盛大恢宏的开幕，" +
                                    "每一幕绮丽迷幻的灯光，都从这里萌芽。在这里甩开多余的规则，扔掉定式的思维，" +
                                    "我们创造属于我们自己的舞台。欢迎加入院文艺部，灵魂和梦想，总有一个在路上。')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('体育部'," +
                                    "'体育部部长'," +
                                    "'我们是至诚学院体育部，我们很重要拓展研究生户外生活，增强研究生身体素质，带领大家“走下网络，走出宿舍，走向操场”。\n" +
                                    "\n" +
                                    "我们做什么？院运会、校运会、红色运动会、篮球赛等!你不需要拥有任何运动技能，也不需要有出色交际能力，" +
                                    "只要你热爱集体生活、对工作认真负责，你就是我们的one pick!如果你热爱运动，热爱生活，并想结交朋友，" +
                                    "那么欢迎你加入我们，我们期待每一个生机蓬勃的你！')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('教育培训部'," +
                                    "'教育培训部部长'," +
                                    "'你想结交更多外部和外系的朋友吗？你想培训PS，剪辑，音响等技术吗？你想发现自己隐藏的实力吗？\n" +
                                    "\n" +
                                    "教育培训部作为院团委的直隶部门，担任着学生会规划者、监督者、培训者的角色，" +
                                    "为学生会吸纳、储备、培训学生干部，促进着学生会工作的制度化、专业化、科学化地发展，" +
                                    "同院学生会各部以及各系有着千丝万缕的联系。\n" +
                                    "\n" +
                                    "相信在教育培训部的每一份工作，都将会让你受益匪浅。心怀热忱，善始善终。我们期待每一个优秀的你。')");

                            db.execSQL("insert into tb_organization(organization_name,person_in_charge,description) values" +
                                    "('人力资源管理部'," +
                                    "'人力资源管理部部长'," +
                                    "'人资部是负责学生会内部事物的管理和分配的一个独立部门，主要的工作包括：" +
                                    "绩效考核，干事培训，干部培训，干部素拓，人事档案管理，换届大会等。\n" +
                                    "\n" +
                                    "进入人资可以锻炼到你的：策划，执行，配合，统筹兼顾等能力，" +
                                    "在这里你会有更多的机会接触到更多学生会的小伙伴，在这里你会明白团结协作也许比一枝独秀更快乐，" +
                                    "在这里你会看到一个优秀的团队从懵懂前行到坚定初心。\n" +
                                    "\n" +
                                    "无论你目前拥有什么样的能力，只要你想来人资，只要你拥有一颗充满热情的心，" +
                                    "我们将义无反顾陪你一起成长，共同进步。')");

                            db.execSQL("insert into tb_activities(organization_id,organization_name,activity_name,activity_start_time,activity_description) values" +
                                    "('13'," +
                                    "'教育培训部'," +
                                    "'关于招募福州大学至诚学院 第二期青年马克思主义知行社学员的通知'," +
                                    "'2019-11-18 10:29:55'," +
                                    "'各系团委，各级学生组织，全体同学：\n" +
                                    "为继续深入学习贯彻落实党的十九大及习近平总书记系列重要讲话精神，推进“不忘初心，牢记使命”主题教育活动，" +
                                    "落实全国高校思想政治工作会议和中央31号文件精神，根据中共中央国务院印发的《中长期青年发展规划（2016—2025年）》要求，" +
                                    "经学院研究，决定在学院实施青年马克思主义者培养工程。现面向学院全体学生招募第二期青年马克思主义知行社学员，" +
                                    "现将有关工作通知如下：\n" +
                                    "一、培养目标\n" +
                                    "福州大学至诚学院青年马克思主义知行社是在院党委领导下，由院团委、马克思主义学院共同管理的青年学生社团。" +
                                    "社团以马克思列宁主义、毛泽东思想、邓小平理论和“三个代表”重要思想、科学发展观、习近平新时代中国特色社会主义思想为指导思想，" +
                                    "通过组织学习、研究、探讨、实践，带动广大同学认真学习贯彻和执行党的基本路线、方针和政策，坚持四项基本原则和党的教育方针，" +
                                    "自觉践行社会主义核心价值观，努力提高全院学生的理论修养和思想认识水平，为学院的改革发展作贡献，" +
                                    "为国家的现代化建设事业奠定坚实的基础。\n" +
                                    "二、培养方式\n" +
                                    "主要从五个方面培养学员：理论学习，实践调研，对外交流，红色教育。\n" +
                                    "三、培训时长\n" +
                                    "为期一年\n" +
                                    "四、政策保障\n" +
                                    "（一）荣誉奖励。在青马社培训期间，学习勤奋、工作积极、成绩显著、表现突出者，可参加优秀学员的评选，" +
                                    "院团委在推优、评优活动中，对知行社的指标予以单列；\n" +
                                    "（二）交流访问。在培训期间表现优异的同学可优先获得学院各类学生外访、实习、交流和培训、竞赛机会；\n" +
                                    "（三）考核合格学员，将颁发结业证书。\n" +
                                    "五、报名条件\n" +
                                    "（一）热爱祖国，热爱中国共产党，政治立场坚定，拥护党的路线、方针、政策；\n" +
                                    "（二）遵纪守法，遵守校规校纪，无不良行为和处分记录；\n" +
                                    "（三）认真学习马克思主义理论基础，对马列主义和中国特色社会主义理论体系有了解和见解；关注时事，对党和国家的重大决策、" +
                                    "社会时事和国内外重大事件有敏锐度和思考；\n" +
                                    "（四）有强烈的责任心和上进心，积极参与学院各类活动，在学术科研、社会实践、志愿服务、学生工作和文化艺术领域有突出表现者；\n" +
                                    "（五）学习成绩优良，无重修科目，成绩排名（不包括综合测评加分）专业前50%。如综合素质特别优秀者，招募成绩可以适当放宽；\n" +
                                    "（六）招募对象至少满足以下一项条件：中共（预备）党员、入党积极分子、现任或曾任院系学生干部、班团干部；\n" +
                                    "（七）此次招募对象是具有福州大学至诚学院学籍的2018级、2019级全日制在校生。\n" +
                                    "六、报名方式\n" +
                                    "本次报名采取各系团委推荐、学生组织推荐和马院教师推荐三种方式。所有报名同学均需按照实际情况认真填写报名表（见附件1），报名表需张贴本人近期一寸电子照片。\n" +
                                    "（1）各系团委推荐名额共24人，按照学生人数比例进行名额分配（见附件2）。各系团委将报名表统一整理并加盖公章后，" +
                                    "请于11月22日12时-13时将纸质版报名表送至院团委办公室（学生工作处103），并将电子版报名表发送至：zcxyrzb@163.com。\n" +
                                    "（2）院级学生组织推荐名额14人左右。报名表由分管指导老师签字，各学生组织统一整理后，" +
                                    "请于11月22日12时-13时前将纸质版报名表送至院团委办公室（学生工作处103），并将电子版报名表发送至：zcxyrzb@163.com 。\n" +
                                    "（3）马院教师推荐人选请于11月22日12时-13时递交纸质版报名表交至院团委办公室（学生工作处103），" +
                                    "并将电子版报名表发至邮箱：zcxyrzb@163.com 。\n" +
                                    "七、院团委负责学生及联系方式\n" +
                                    "联系人：石利民\n" +
                                    "联系电话：17720771309')");



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

    public abstract OrganizationDao getOrganizationDao();

    public abstract ActivitiesDao getActivitiesDao();
}
