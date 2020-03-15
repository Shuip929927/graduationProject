package cn.yangcy.pzc_server.bean;

public class Information {

    private Integer id;
    private String type;
    private String title;
    private String author;
    private String createOn;
    private String content;
    private Integer isDelete;
    //  0代表未删除 1代表删除
    private Integer hits;

    public Information(Integer id, String type, String title, String author, String createOn,
                       String content, Integer isDelete, Integer hits) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.author = author;
        this.createOn = createOn;
        this.content = content;
        this.isDelete = isDelete;
        this.hits = hits;
    }

    public Information(String type, String title, String author, String createOn, String content) {
        this.type = type;
        this.title = title;
        this.author = author;
        this.createOn = createOn;
        this.content = content;
        this.isDelete = 0;
        this.hits = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        return "Information[" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", createOn='" + createOn + '\'' +
                ", content='" + content + '\'' +
                ", isDelete=" + isDelete +
                ", hits=" + hits +
                ']';
    }
}
