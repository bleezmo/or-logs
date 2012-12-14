package models

import play.api.Play.current
import com.novus.salat._
import com.novus.salat.annotations._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import dao.SalatMongoCursor
import se.radley.plugin.salat._
import mongoContext._
import play.api.libs.json._
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.libs.json.JsNumber
import java.text.SimpleDateFormat
import java.util.Date

case class OrLog(timestamp: Long, ornode: Option[String], message: String){
  def toJson:JsValue = {
    val sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
    val resultdate = new Date(timestamp);
    JsObject(Seq(
      "timestamp" -> JsString(sdf.format(resultdate)),
      "ornode" -> JsString(ornode.getOrElse("")),
      "message" -> JsString(message)
    ))
  }
}
object OrLog extends ModelCompanion[OrLog,ObjectId]{
  val dao = new SalatDAO[OrLog,ObjectId](collection = mongoCollection("orlog")) {}
  def getLogs:JsArray = {
    val c:SalatMongoCursor[OrLog] = dao.find(MongoDBObject()).sort(MongoDBObject("timestamp" -> 1))
    val orlogs:Seq[JsValue] = c.foldRight[Seq[JsValue]](Seq())((orlog,acc) => acc :+ orlog.toJson)
    JsArray(orlogs)
  }
}
//class ORLog{
//  private final long timestamp = new Date().getTime();
//  private final String ornode;
//  private final String message;
//  public long getTimestamp() {
//    return timestamp;
//  }
//  public String getOrnode() {
//    return ornode;
//  }
//  public String getMessage() {
//    return message;
//  }
//  public ORLog(String ornode, String message){
//    this.ornode = ornode;
//    this.message = message;
//  }
//}
