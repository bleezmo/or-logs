package controllers

import play.api._
import play.api.mvc._
import models.OrLog

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index())
  }

  def logs = Action{
    Ok(OrLog.getLogs);
  }
}