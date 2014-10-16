var TreeMenu=new function()
{
	this._jsevent=null;
    this._url=null;
	this._img=null;
    document.write('<style type="text/css">');
    document.write('.treeCheckBox{height:11px; width:11px;vertical-align:middle}');
    document.write('.treeImg{cursor:pointer;vertical-align:text-bottom;margin-right:2px}');
    document.write('</style>');
	this.icon=new Array();

	this.getChildren=function(_parentId)
	{
		this.icon["member"]=this._img+'child.gif';
	    this.icon["open"]=this._img+'opened.gif';
	    this.icon["close"]=this._img+'closed.gif';
	    if (this.alreadyGetChileren(_parentId))
		{
		    var childContainer=document.getElementById(_parentId+"_subContainer");
		    if (childContainer)
		    {
		        childContainer.style.display=(childContainer.style.display=="none")?"":"none";
		        var _parentNode=document.getElementById(_parentId);
		        if (_parentNode.firstChild && _parentNode.firstChild.tagName=="IMG")
		        {
		            _parentNode.firstChild.src=(childContainer.style.display=="none")?this.icon["close"]:this.icon["open"];
		        }
		    }
		    return;
		}
		var processRequest=function(obj)
		{
		    TreeMenu.addChildren(_parentId,obj.responseXML);
		}
		Request.send(this._url+"?pId="+_parentId,"sql="+document.all.treesql.value,processRequest,_parentId+"");
	}
	this.addChildren=function(_parentId,_data)
	{
	    if (this.alreadyGetChileren(_parentId))
	    {
	        return;
	    }

	    var _parentNode=document.getElementById(_parentId);
	    if (_parentNode.firstChild && _parentNode.firstChild.tagName=="IMG")
	    {
	        _parentNode.firstChild.src=this.icon["open"];
	    }
	    _nodeContainer=document.createElement("div");
		_nodeContainer.id=_parentId+"_subContainer";
		_parentNode.appendChild(_nodeContainer);

		var _children=_data.getElementsByTagName("root")[0].childNodes;

		var _child=null;
		var _point=this;
		for(var i=0; i<_children.length; i++)
		{
			_child=_children[i];
			_node=document.createElement("div");
			if (i!=_children.length-1)
			{
			    _node.style.cssText="padding-bottom:5px";
			}
			_node.innerHTML="";
			_node.id=_child.getAttribute("id");
			_node.parentId = _child.getAttribute("parentId");
                        _node.hasChildren = _child.getAttribute("hasChildren");
			if (_child.getAttribute("hasChildren")=="1")
			{
			    _node.innerHTML+='<img class="treeImg" onclick="TreeMenu.getChildren('+_child.getAttribute("id")+')" src="'+this.icon["close"]+'"/>';
			    _node.innerHTML+='<span style="cursor:pointer;line-height:16px;height:16px" name="treeText" onclick="treeNodeChoosed(this);">'+_child.firstChild.data+'</span>';
			}
			else if (_child.getAttribute("hasChildren")==0)
			{
			    _node.innerHTML+='<img class="treeImg" onclick="try{treeNodeChoosed(this.nextSibling);}catch(e){alert(e.message);}" src="'+this.icon["member"]+'" style="margin-left:14px"/>';
			    _node.innerHTML+='<span style="cursor:pointer;line-height:16px;height:16px" name="treeText" onclick="treeNodeChoosed(this);">'+_child.firstChild.data+'</span>';
			}
			_nodeContainer.appendChild(_node);
		}
		_nodeContainer.style.cssText="border-left:0px solid #ccc;margin-left:7px;margin-top:5px;padding-left:10px";
	}
	this.alreadyGetChileren=function(_nodeId)
	{
	    var obj=document.getElementById(_nodeId+"_subContainer");
	    if (obj)
	    {
	        return true;
	    }
	    return false;
	}
}
// add a new child node
function addNewChildNode(_parentId,_childId,_childData)
{
  var _parentNode=document.getElementById(_parentId);
  var _node=document.createElement("div");
  _node.style.cssText="padding-bottom:5px";
  _node.innerHTML="";
  _node.id=_childId;
  _node.parentId = _parentId;
  _node.innerHTML+='<img class="treeImg" onclick="try{treeNodeChoosed(this.nextSibling);}catch(e){alert(e.message);}" src="'+this.icon["member"]+'" style="margin-left:14px"/>';
  _node.innerHTML+='<span style="cursor:pointer;line-height:16px;height:16px" name="treeText" onclick="treeNodeChoosed(this);">'+_childData+'</span>';
  _parentNode.append(_node);
}
function treeNodeChoosed(_obj)
{
    var choosedColor="lightblue";
    var unChoosedColor="white";

    if (_obj.style.backgroundColor==choosedColor)
    {
        _obj.style.backgroundColor=unChoosedColor;
    }
    else
    {
        //var allNodeText=document.getElementsByName("treeText");
        var allNodeText=document.getElementsByTagName("SPAN");
        for (var i=0; i<allNodeText.length; i++)
        {
            allNodeText[i].style.backgroundColor=unChoosedColor;
        }
        _obj.style.backgroundColor=choosedColor;
    }
    //execute additional function
    if (document.all.treetag_lineClickEvent)
         eval(document.all.treetag_lineClickEvent.value+"(_obj.parentElement)");
	var nodeId = _obj.parentElement.id;
	eval(TreeMenu._jsevent+"(_obj.parentElement)");
}
