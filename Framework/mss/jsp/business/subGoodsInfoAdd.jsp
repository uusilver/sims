<%@page contentType="text/html; charset=utf-8"%>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
				<tr>
					<td valign="middle" rowspan="9" width="5%">
						<img alt="" style="cursor:pointer;" src="${contextPath}/mss/image/016.jpg" onclick="combineGoodsType();"/>
					</td>
					<td align="center">
						<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="qinggoudan_table">
					<input type="hidden" name="goodsNum" value="${information.goodsInfo.goodsNum }" />
					<tr height="30">
						<td width="20%" align="center" class="qinggoudan_table_title">
							物品名称
							<font color="red">*</font>
						</td>
						<td align="left" class="qinggoudan_table_td1">
							<input name="goodsName" id="goods_name" type="text" class="qinggoudan_input023" size="20" maxlength="50" readonly="readonly"
								value="${information.goodsInfo.goodsName}"/>
							<span id="goods_nameDiv"></span>
						</td>
					</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品编号
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsCode" id="goods_code" type="text" class="qinggoudan_input023" size="20" maxlength="50" readonly="readonly"
							value="${information.goodsInfo.goodsCode}"/>
						<span id="goods_nameDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						所属类别
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsType" id="goods_type" type="text" class="qinggoudan_input023" size="20" maxlength="20" readonly="readonly"
							value="${information.goodsInfo.typeName}"/>
							<input type="hidden" name="goodsTypeStr" id="goods_type_str" value="${information.goodsInfo.goodsType}" />
						<span id="goods_typeDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						成本单价
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsPrice" id="goods_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsInfo.goodsPrice}"
							onchange="checkGoodsPrice();">
							元&nbsp;(请按000.00格式填写)
					<span id="goods_priceDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						预估单价
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="wishPrice" id="wish_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsInfo.wishPrice}"
							onchange="checkPrice('wish_price');">
							元&nbsp;(请按000.00格式填写)
					<span id="wish_priceDiv"></span>
					</td>
				</tr>
				<!-- tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						销售单价
						<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="salePrice" id="sale_price" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsInfo.goodsPrice}"
							onchange="checkGoodsPrice()">
							元&nbsp;(请按000.00格式填写)
					<span id="goods_priceDiv"></span>
					</td>
				</tr> -->
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品数量
							<font color="red">*</font>
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<input name="goodsCount" id="goods_count" type="text" class="qinggoudan_input02" size="20" maxlength="50"
							value="${information.goodsInfo.goodsCount}"
							onchange="checkGoodsCount();">
					<span id="goods_countDiv"></span>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						物品状态
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<select name="goodsState">
						<c:if test="${information.goodsInfo.goodsState==null}">
						<option value="A">有效</option>
						<option value="U">无效</option>
						</c:if>
						<c:if test="${information.goodsInfo.goodsState=='A'}">
						<option value="A" selected="selected">有效</option>
						<option value="U">无效</option>
						</c:if>
						<c:if test="${information.goodsInfo.goodsState=='U'}">
						<option value="A">有效</option>
						<option value="U" selected="selected">无效</option>
						</c:if>
						</select>
					</td>
				</tr>
				<tr height="30">
					<td width="20%" align="center" class="qinggoudan_table_title">
						备注
					</td>
					<td align="left" class="qinggoudan_table_td1">
						<textarea name="goodsComment" id="goodsComment" rows="10" cols="30" onchange="checkGoodsComment()">${information.goodsInfo.goodsComm}</textarea>
					<span id="goods_commDiv"></span>
					</td>
				</tr>

			</table>
					</td>
				</tr>
			</table>