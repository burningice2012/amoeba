package com.meidusa.amoeba.mongodb.packet;

import java.io.UnsupportedEncodingException;

import org.bson.BSONObject;

import com.meidusa.amoeba.mongodb.io.MongodbPacketConstant;

/**
 * <h6><a name="MongoWireProtocol-OPQUERY"></a>OP_QUERY <a name="MongoWireProtocol-OPQUERY"></a></h6>
 * 
 * <p>The OP_QUERY message is used to query the database for documents in a collection.  The format of the OP_QUERY message is :</p>
 * 
 * <div class="code panel" style="border-width: 1px;"><div class="codeContent panelContent">
 * <pre class="code-java">struct OP_QUERY {
 *     MsgHeader header;                <span class="code-comment">// standard message header
 * </span>    int32     flags;                  <span class="code-comment">// bit vector of query options.  See below <span class="code-keyword">for</span> details.
 * 
 * </span>    cstring   fullCollectionName;    <span class="code-comment">// <span class="code-quote">"dbname.collectionname"</span>
 * </span>    int32     numberToSkip;          <span class="code-comment">// number of documents to skip
 * </span>    int32     numberToReturn;        <span class="code-comment">// number of documents to <span class="code-keyword">return</span>
 * </span>                                     <span class="code-comment">//  in the first OP_REPLY batch
 * </span>    document  query;                 <span class="code-comment">// query object.  See below <span class="code-keyword">for</span> details.
 * 
 * </span>  [ document  returnFieldSelector; ] <span class="code-comment">// Optional. Selector indicating the fields
 * </span>                                     <span class="code-comment">//  to <span class="code-keyword">return</span>.  See below <span class="code-keyword">for</span> details.
 * </span>}
 * </pre>
 * 
 * @author Struct
 *
 */
public class QueryMongodbPacket extends AbstractMongodbPacket {

	public int flags;
	public String fullCollectionName;
	public int numberToSkip;
	public int numberToReturn;
	public BSONObject query;
	public BSONObject returnFieldSelector;
	public QueryMongodbPacket(){
		this.opCode = MongodbPacketConstant.OP_QUERY;
	}
	protected void init(MongodbPacketBuffer buffer) {
		super.init(buffer);
		flags = buffer.readInt();
		fullCollectionName = buffer.readCString();
		numberToSkip = buffer.readInt();
		numberToReturn = buffer.readInt();
		query = buffer.readBSONObject();
		if(buffer.hasRemaining()){
			returnFieldSelector  = buffer.readBSONObject();
		}
	}
	
	protected void write2Buffer(MongodbPacketBuffer buffer)
	throws UnsupportedEncodingException {
		super.write2Buffer(buffer);
		buffer.writeInt(flags);
		buffer.writeCString(fullCollectionName);
		buffer.writeInt(numberToSkip);
		buffer.writeInt(numberToReturn);
		buffer.writeBSONObject(query);
		if(returnFieldSelector != null){
			buffer.writeBSONObject(returnFieldSelector);
		}
	}
	
}
