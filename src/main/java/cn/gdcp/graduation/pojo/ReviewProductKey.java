package cn.gdcp.graduation.pojo;

import javax.persistence.Table;

@Table(name = "u_review_product_key")
public class ReviewProductKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_review_product_key.ur_id
     *
     * @mbggenerated Sun Dec 01 19:24:26 CST 2019
     */
    private Integer urId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_review_product_key.p_id
     *
     * @mbggenerated Sun Dec 01 19:24:26 CST 2019
     */
    private Integer pId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_review_product_key.ur_id
     *
     * @return the value of u_review_product_key.ur_id
     *
     * @mbggenerated Sun Dec 01 19:24:26 CST 2019
     */
    public Integer getUrId() {
        return urId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_review_product_key.ur_id
     *
     * @param urId the value for u_review_product_key.ur_id
     *
     * @mbggenerated Sun Dec 01 19:24:26 CST 2019
     */
    public void setUrId(Integer urId) {
        this.urId = urId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_review_product_key.p_id
     *
     * @return the value of u_review_product_key.p_id
     *
     * @mbggenerated Sun Dec 01 19:24:26 CST 2019
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_review_product_key.p_id
     *
     * @param pId the value for u_review_product_key.p_id
     *
     * @mbggenerated Sun Dec 01 19:24:26 CST 2019
     */
    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
