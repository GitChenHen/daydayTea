package cn.gdcp.graduation.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "c_productimage")
public class ProductImage {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_productimage.id
     *
     * @mbggenerated Fri Nov 08 22:48:31 CST 2019
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY,generator="Mysql")
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column c_productimage.type
     *
     * @mbggenerated Fri Nov 08 22:48:31 CST 2019
     */
    private String type;

    private Integer pid;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_productimage.id
     *
     * @return the value of c_productimage.id
     *
     * @mbggenerated Fri Nov 08 22:48:31 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_productimage.id
     *
     * @param id the value for c_productimage.id
     *
     * @mbggenerated Fri Nov 08 22:48:31 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column c_productimage.type
     *
     * @return the value of c_productimage.type
     *
     * @mbggenerated Fri Nov 08 22:48:31 CST 2019
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column c_productimage.type
     *
     * @param type the value for c_productimage.type
     *
     * @mbggenerated Fri Nov 08 22:48:31 CST 2019
     */
    public void setType(String type) {
        this.type = type;
    }
}