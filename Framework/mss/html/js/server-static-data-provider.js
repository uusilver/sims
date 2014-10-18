/**
 * Created with JetBrains WebStorm.
 * User: vali
 * Date: 14-10-18
 * Time: 下午1:14
 * To change this template use File | Settings | File Templates.
 */

jQuery(document).ready(function() {

    //fusion charts loading
    if (GALLERY_RENDERER && GALLERY_RENDERER.search(/javascript|flash/i)==0)  FusionCharts.setCurrentRenderer(GALLERY_RENDERER);
    var chart = new FusionCharts("charts/Pie3D.swf", "ChartId", "600", "400", "0", "0");
    chart.setXMLData( dataString );

    chart.render("exhibitorright");

    if (GALLERY_RENDERER && GALLERY_RENDERER.search(/javascript|flash/i)==0)  FusionCharts.setCurrentRenderer(GALLERY_RENDERER);
    var chart1 = new FusionCharts("charts/Column3D.swf", "ChartId1", "600", "400", "0", "0");
    chart1.setXMLData( dataString1 );
    chart1.render("exhibitorleft");
});
