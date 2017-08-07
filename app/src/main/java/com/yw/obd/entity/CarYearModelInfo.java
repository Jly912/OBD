package com.yw.obd.entity;

import java.util.List;

/**
 * Created by apollo on 2017/8/4.
 */

public class CarYearModelInfo {

    /**
     * status : 0
     * msg : ok
     * result : {"id":"221","name":"A4L","initial":"A","fullname":"奥迪A4L","logo":"http://api.jisuapi.com/car/static/images/logo/300/221.jpg","salestate":"在销","depth":"3","list":[{"id":"2623","name":"2015款 45 TFSI quattro 个性运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2623.jpg","price":"35.3万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2625","name":"2015款 50 TFSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2625.jpg","price":"57.81万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2626","name":"2013款 30 TFSI 手动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2626.jpg","price":"27.28万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2628","name":"2013款 35 TFSI 自动 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2628.JPG","price":"30.98万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2632","name":"2013款 40 TFSI quattro 个性运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2632.JPG","price":"34.9万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2634","name":"2013款 50 TFSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2634.jpg","price":"57.81万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2635","name":"2012款 1.8 TFSI 手动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2635.jpg","price":"27.28万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2637","name":"2012款 2.0 TFSI(132kW) 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2637.jpg","price":"30.98万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2641","name":"2012款 2.0 TFSI(155kW) 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2641.jpg","price":"42.38万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2643","name":"2011款 1.8 TFSI 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2643.jpg","price":"29.1万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2644","name":"2011款 2.0 TFSI(132kW) 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2644.JPG","price":"30.98万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2648","name":"2011款 2.0 TFSI(155kW) 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2648.JPG","price":"42.38万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2650","name":"2011款 3.2 FSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2650.JPG","price":"57.81万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2651","name":"2010款 1.8 TFSI 手动舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2651.jpg","price":"27.1万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2652","name":"2010款 1.8TFSI 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2652.jpg","price":"29.1万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2653","name":"2010款 2.0TFSI 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2653.jpg","price":"30.98万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2657","name":"2010款 2.0TFSI 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2657.jpg","price":"42.38万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2658","name":"2010款 3.2FSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2658.jpg","price":"57.81万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2659","name":"2009款 2.0 TFSI 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2659.jpg","price":"29.88万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2663","name":"2009款 3.2 FSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2663.JPG","price":"53.88万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2604","name":"2015款 30 TFSI 手动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2604.jpg","price":"27.28万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2614","name":"2015款 35 TFSI 纪念智领版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2614.JPG","price":"30.56万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2605","name":"2015款 30 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2605.jpg","price":"29.1万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2654","name":"2010款 2.0TFSI 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2654.jpg","price":"32.99万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2649","name":"2011款 2.0 TFSI(155kW) 尊享型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2649.JPG","price":"47.12万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2645","name":"2011款 2.0 TFSI(132kW) 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2645.JPG","price":"32.99万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2642","name":"2012款 2.0 TFSI(155kW) 尊享型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2642.jpg","price":"46.45万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2638","name":"2012款 2.0 TFSI(132kW) 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2638.jpg","price":"32.99万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2660","name":"2009款 2.0 TFSI 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2660.jpg","price":"32.99万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2636","name":"2012款 1.8 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2636.jpg","price":"29.1万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2615","name":"2015款 35 TFSI 自动 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2615.JPG","price":"31.28万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2633","name":"2013款 40 TFSI quattro 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2633.jpg","price":"46.96万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2629","name":"2013款 35 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2629.jpg","price":"32.99万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2624","name":"2015款 45 TFSI quattro 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2624.jpg","price":"47.36万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2627","name":"2013款 30 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2627.JPG","price":"29.1万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2655","name":"2010款 2.0TFSI 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2655.jpg","price":"36.98万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2661","name":"2009款 2.0 TFSI 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2661.jpg","price":"36.18万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2646","name":"2011款 2.0 TFSI(132kW) 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2646.JPG","price":"36.98万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2639","name":"2012款 2.0 TFSI(132kW) 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2639.jpg","price":"36.98万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2630","name":"2013款 35 TFSI 自动 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2630.JPG","price":"36.98万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2616","name":"2015款 35 TFSI 纪念舒享版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2616.JPG","price":"32.16万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2640","name":"2012款 2.0 TFSI(132kW) 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2640.jpg","price":"39.9万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2662","name":"2009款 2.0 TFSI 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2662.JPG","price":"38.86万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2617","name":"2015款 35 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2617.JPG","price":"33.29万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2647","name":"2011款 2.0 TFSI(132kW) 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2647.JPG","price":"39.9万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2656","name":"2010款 2.0TFSI 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2656.jpg","price":"39.9万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2631","name":"2013款 35 TFSI 自动 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2631.JPG","price":"39.9万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2618","name":"2015款 35 TFSI 自动 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2618.JPG","price":"37.28万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2619","name":"2015款 35 TFSI 自动 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2619.JPG","price":"40.2万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"37165","name":"2017款 45 TFSI quattro 风尚型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37165.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37163","name":"2017款 40 TFSI 风尚型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37163.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37164","name":"2017款 45 TFSI quattro 运动型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37164.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37162","name":"2017款 40 TFSI 运动型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37162.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37161","name":"2017款 40 TFSI 时尚型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37161.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37160","name":"2017款 40 TFSI 进取型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37160.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"34288","name":"2016款 50 TFSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34288.jpg","price":"","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34287","name":"2017款 45 TFSI quattro 特别版","logo":"http://api.jisuapi.com/car/static/images/logo/300/34287.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34286","name":"2017款 45 TFSI quattro 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34286.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34285","name":"2017款 45 TFSI quattro 风尚型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34285.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34284","name":"2017款 40 TFSI 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34284.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34283","name":"2017款 40 TFSI 风尚型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34283.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34281","name":"2017款 40 TFSI 进取型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34281.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34282","name":"2017款 40 TFSI 时尚型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34282.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2622","name":"2016款 45 TFSI quattro 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2622.jpg","price":"46.46万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2621","name":"2016款 45 TFSI quattro 个性运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2621.JPG","price":"36.30万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2620","name":"2016款 45 TFSI quattro 个性运动型 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2620.jpg","price":"36.30万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2613","name":"2016款 35 TFSI 自动 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2613.jpg","price":"40.20万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2612","name":"2016款 35 TFSI 自动 豪华型 S line 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2612.JPG","price":"39.39万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2611","name":"2016款 35 TFSI 自动 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2611.jpg","price":"37.28万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2610","name":"2016款 35 TFSI 自动 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2610.jpg","price":"34.90万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2609","name":"2016款 35 TFSI 自动 舒适型 S line 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2609.jpg","price":"33.29万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2608","name":"2016款 35 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2608.jpg","price":"33.29万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2607","name":"2016款 35 TFSI 自动 标准型 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2607.JPG","price":"31.28万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2606","name":"2016款 35 TFSI 自动 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2606.JPG","price":"31.28万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2603","name":"2016款 30 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2603.jpg","price":"29.10万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2602","name":"2016款 30 TFSI 自动 舒适型 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2602.jpg","price":"28.99万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2601","name":"2016款 30 TFSI 手动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2601.jpg","price":"27.28万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"}]}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 221
         * name : A4L
         * initial : A
         * fullname : 奥迪A4L
         * logo : http://api.jisuapi.com/car/static/images/logo/300/221.jpg
         * salestate : 在销
         * depth : 3
         * list : [{"id":"2623","name":"2015款 45 TFSI quattro 个性运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2623.jpg","price":"35.3万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2625","name":"2015款 50 TFSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2625.jpg","price":"57.81万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2626","name":"2013款 30 TFSI 手动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2626.jpg","price":"27.28万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2628","name":"2013款 35 TFSI 自动 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2628.JPG","price":"30.98万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2632","name":"2013款 40 TFSI quattro 个性运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2632.JPG","price":"34.9万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2634","name":"2013款 50 TFSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2634.jpg","price":"57.81万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2635","name":"2012款 1.8 TFSI 手动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2635.jpg","price":"27.28万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2637","name":"2012款 2.0 TFSI(132kW) 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2637.jpg","price":"30.98万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2641","name":"2012款 2.0 TFSI(155kW) 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2641.jpg","price":"42.38万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2643","name":"2011款 1.8 TFSI 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2643.jpg","price":"29.1万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2644","name":"2011款 2.0 TFSI(132kW) 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2644.JPG","price":"30.98万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2648","name":"2011款 2.0 TFSI(155kW) 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2648.JPG","price":"42.38万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2650","name":"2011款 3.2 FSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2650.JPG","price":"57.81万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2651","name":"2010款 1.8 TFSI 手动舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2651.jpg","price":"27.1万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2652","name":"2010款 1.8TFSI 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2652.jpg","price":"29.1万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2653","name":"2010款 2.0TFSI 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2653.jpg","price":"30.98万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2657","name":"2010款 2.0TFSI 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2657.jpg","price":"42.38万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2658","name":"2010款 3.2FSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2658.jpg","price":"57.81万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2659","name":"2009款 2.0 TFSI 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2659.jpg","price":"29.88万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2663","name":"2009款 3.2 FSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2663.JPG","price":"53.88万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2604","name":"2015款 30 TFSI 手动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2604.jpg","price":"27.28万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2614","name":"2015款 35 TFSI 纪念智领版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2614.JPG","price":"30.56万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2605","name":"2015款 30 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2605.jpg","price":"29.1万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2654","name":"2010款 2.0TFSI 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2654.jpg","price":"32.99万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2649","name":"2011款 2.0 TFSI(155kW) 尊享型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2649.JPG","price":"47.12万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2645","name":"2011款 2.0 TFSI(132kW) 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2645.JPG","price":"32.99万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2642","name":"2012款 2.0 TFSI(155kW) 尊享型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2642.jpg","price":"46.45万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2638","name":"2012款 2.0 TFSI(132kW) 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2638.jpg","price":"32.99万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2660","name":"2009款 2.0 TFSI 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2660.jpg","price":"32.99万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2636","name":"2012款 1.8 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2636.jpg","price":"29.1万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2615","name":"2015款 35 TFSI 自动 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2615.JPG","price":"31.28万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2633","name":"2013款 40 TFSI quattro 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2633.jpg","price":"46.96万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2629","name":"2013款 35 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2629.jpg","price":"32.99万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2624","name":"2015款 45 TFSI quattro 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2624.jpg","price":"47.36万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2627","name":"2013款 30 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2627.JPG","price":"29.1万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2655","name":"2010款 2.0TFSI 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2655.jpg","price":"36.98万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2661","name":"2009款 2.0 TFSI 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2661.jpg","price":"36.18万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2646","name":"2011款 2.0 TFSI(132kW) 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2646.JPG","price":"36.98万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2639","name":"2012款 2.0 TFSI(132kW) 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2639.jpg","price":"36.98万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2630","name":"2013款 35 TFSI 自动 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2630.JPG","price":"36.98万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2616","name":"2015款 35 TFSI 纪念舒享版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2616.JPG","price":"32.16万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2640","name":"2012款 2.0 TFSI(132kW) 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2640.jpg","price":"39.9万","yeartype":"2012","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2662","name":"2009款 2.0 TFSI 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2662.JPG","price":"38.86万","yeartype":"2009","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2617","name":"2015款 35 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2617.JPG","price":"33.29万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2647","name":"2011款 2.0 TFSI(132kW) 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2647.JPG","price":"39.9万","yeartype":"2011","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2656","name":"2010款 2.0TFSI 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2656.jpg","price":"39.9万","yeartype":"2010","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2631","name":"2013款 35 TFSI 自动 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2631.JPG","price":"39.9万","yeartype":"2013","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2618","name":"2015款 35 TFSI 自动 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2618.JPG","price":"37.28万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"2619","name":"2015款 35 TFSI 自动 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2619.JPG","price":"40.2万","yeartype":"2015","productionstate":"停产","salestate":"停产","sizetype":"中型车"},{"id":"37165","name":"2017款 45 TFSI quattro 风尚型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37165.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37163","name":"2017款 40 TFSI 风尚型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37163.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37164","name":"2017款 45 TFSI quattro 运动型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37164.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37162","name":"2017款 40 TFSI 运动型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37162.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37161","name":"2017款 40 TFSI 时尚型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37161.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"37160","name":"2017款 40 TFSI 进取型 Plus","logo":"http://api.jisuapi.com/car/static/images/logo/300/37160.jpg","price":"","yeartype":"2017","productionstate":"在产","salestate":"在销","sizetype":"中型车"},{"id":"34288","name":"2016款 50 TFSI quattro 旗舰型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34288.jpg","price":"","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34287","name":"2017款 45 TFSI quattro 特别版","logo":"http://api.jisuapi.com/car/static/images/logo/300/34287.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34286","name":"2017款 45 TFSI quattro 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34286.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34285","name":"2017款 45 TFSI quattro 风尚型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34285.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34284","name":"2017款 40 TFSI 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34284.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34283","name":"2017款 40 TFSI 风尚型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34283.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34281","name":"2017款 40 TFSI 进取型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34281.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"34282","name":"2017款 40 TFSI 时尚型","logo":"http://api.jisuapi.com/car/static/images/logo/300/34282.jpg","price":"","yeartype":"2017","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2622","name":"2016款 45 TFSI quattro 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2622.jpg","price":"46.46万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2621","name":"2016款 45 TFSI quattro 个性运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2621.JPG","price":"36.30万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2620","name":"2016款 45 TFSI quattro 个性运动型 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2620.jpg","price":"36.30万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2613","name":"2016款 35 TFSI 自动 豪华型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2613.jpg","price":"40.20万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2612","name":"2016款 35 TFSI 自动 豪华型 S line 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2612.JPG","price":"39.39万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2611","name":"2016款 35 TFSI 自动 技术型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2611.jpg","price":"37.28万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2610","name":"2016款 35 TFSI 自动 运动型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2610.jpg","price":"34.90万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2609","name":"2016款 35 TFSI 自动 舒适型 S line 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2609.jpg","price":"33.29万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2608","name":"2016款 35 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2608.jpg","price":"33.29万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2607","name":"2016款 35 TFSI 自动 标准型 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2607.JPG","price":"31.28万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2606","name":"2016款 35 TFSI 自动 标准型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2606.JPG","price":"31.28万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2603","name":"2016款 30 TFSI 自动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2603.jpg","price":"29.10万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2602","name":"2016款 30 TFSI 自动 舒适型 典藏版","logo":"http://api.jisuapi.com/car/static/images/logo/300/2602.jpg","price":"28.99万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"},{"id":"2601","name":"2016款 30 TFSI 手动 舒适型","logo":"http://api.jisuapi.com/car/static/images/logo/300/2601.jpg","price":"27.28万","yeartype":"2016","productionstate":"停产","salestate":"在销","sizetype":"中型车"}]
         */

        private String id;
        private String name;
        private String initial;
        private String fullname;
        private String logo;
        private String salestate;
        private String depth;
        private List<ListBean> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getSalestate() {
            return salestate;
        }

        public void setSalestate(String salestate) {
            this.salestate = salestate;
        }

        public String getDepth() {
            return depth;
        }

        public void setDepth(String depth) {
            this.depth = depth;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 2623
             * name : 2015款 45 TFSI quattro 个性运动型
             * logo : http://api.jisuapi.com/car/static/images/logo/300/2623.jpg
             * price : 35.3万
             * yeartype : 2015
             * productionstate : 停产
             * salestate : 停产
             * sizetype : 中型车
             */

            private String id;
            private String name;
            private String logo;
            private String price;
            private String yeartype;
            private String productionstate;
            private String salestate;
            private String sizetype;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getYeartype() {
                return yeartype;
            }

            public void setYeartype(String yeartype) {
                this.yeartype = yeartype;
            }

            public String getProductionstate() {
                return productionstate;
            }

            public void setProductionstate(String productionstate) {
                this.productionstate = productionstate;
            }

            public String getSalestate() {
                return salestate;
            }

            public void setSalestate(String salestate) {
                this.salestate = salestate;
            }

            public String getSizetype() {
                return sizetype;
            }

            public void setSizetype(String sizetype) {
                this.sizetype = sizetype;
            }
        }
    }
}
