/*
 * 	This program is free software; you can redistribute it and/or modify it under the terms of 
 * the GNU AFFERO GENERAL PUBLIC LICENSE as published by the Free Software Foundation; either version 3 of the License, 
 * or (at your option) any later version. 
 * 
 * 	This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU AFFERO GENERAL PUBLIC LICENSE for more details. 
 * 	You should have received a copy of the GNU AFFERO GENERAL PUBLIC LICENSE along with this program; 
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.meidusa.amoeba.parser.function;

import java.util.Iterator;
import java.util.List;

import com.meidusa.amoeba.parser.expression.Expression;
import com.meidusa.amoeba.sqljep.ParseException;

/**
 * 
 * @author <a href=mailto:piratebase@sina.com>Struct chen</a>
 *
 */
public class ConcatWs extends AbstractFunction {

	@SuppressWarnings("unchecked")
	public Comparable evaluate(List<Expression> list, Object[] parameters)
			throws ParseException {
		Comparable sparator = null;
		Comparable param1 = null;
		if(list.size()>=3){
			Iterator<Expression> it =list.iterator();
			sparator = it.next().evaluate(parameters);
			param1 = it.next().evaluate(parameters);
			Comparable param2 = it.next().evaluate(parameters);
			param1 = com.meidusa.amoeba.sqljep.function.Concat.concat(param1, sparator,param2);
			for(;it.hasNext();){
				param2 = it.next().evaluate(parameters);
				param1 = com.meidusa.amoeba.sqljep.function.Concat.concat(param1, sparator,param2);
			}
		}
		return param1;
	}

}