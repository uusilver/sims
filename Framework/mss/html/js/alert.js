/**
 * Created with JetBrains WebStorm.
 * User: vali
 * Date: 14-11-17
 * Time: 下午8:39
 * To change this template use File | Settings | File Templates.
 */
window.alert = function(txt)
{
    var shield = document.createElement("DIV");
    shield.id = "shield";
    shield.style.position = "absolute";
    shield.style.left = "0px";
    shield.style.top = "0px";
    shield.style.width = "100%";
    shield.style.height = document.body.scrollHeight+"px";
    shield.style.textAlign = "center";
    shield.style.zIndex = "10000";
    shield.style.filter = "alpha(opacity=0)";
    var alertFram = document.createElement("DIV");
    alertFram.id="alertFram";
    alertFram.style.position = "absolute";
    alertFram.style.left = "50%";
    alertFram.style.top = "50%";
    alertFram.style.marginLeft = "-225px";
    alertFram.style.marginTop = "-75px";
    alertFram.style.width = "450px";
    alertFram.style.height = "150px";
    alertFram.style.background = "#ccc";
    alertFram.style.textAlign = "center";
    alertFram.style.lineHeight = "150px";
    alertFram.style.zIndex = "10001";
    strHtml = "<ul style=\"list-style:none;margin:0px;padding:0px;width:100%\">\n";
    strHtml += " <li style=\"background:#4989D3;text-align:left;padding-left:20px;font-size:14px;font-weight:bold;color:#fff;height:25px;line-height:25px;border:1px solid #4b76f9;\">[SIMS系统提示]</li>\n";
    strHtml += " <li style=\"background:#fff;text-align:center;font-size:12px;height:120px;line-height:120px;border-left:1px solid #F9CADE;border-right:1px solid #F9CADE;\">"+txt+"</li>\n";
    strHtml += " <li style=\"background:#fff;text-align:center;font-weight:bold;height:25px;line-height:25px; border:1px solid #335ff9;\"><input type=\"button\" value=\"确 定\" onclick=\"doOk()\" /></li>\n";
    strHtml += "</ul>\n";
    alertFram.innerHTML = strHtml;
    document.body.appendChild(alertFram);
    document.body.appendChild(shield);
    var c = 0;
    this.doAlpha = function(){
        if (c++ > 20){clearInterval(ad);return 0;}
        shield.style.filter = "alpha(opacity="+c+");";
    }
    var ad = setInterval("doAlpha()",5);
    this.doOk = function(){
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
    alertFram.focus();
    document.body.onselectstart = function(){return false;};
}
