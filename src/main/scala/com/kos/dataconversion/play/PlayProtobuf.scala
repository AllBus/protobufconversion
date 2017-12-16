package com.kos.dataconversion.play

import akka.util.ByteString
import com.trueaccord.scalapb.{GeneratedMessage, GeneratedMessageCompanion}
import play.api.http.Writeable
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
  * Methods for convert Protobuf
  * Created by Kos on 14.12.2017.
  */
object PlayProtobuf {

	final val MIME_TYPE_PROTO = "application/x-protobuf"
	final val OPTION_MIME_TYPE_PROTO = Option(MIME_TYPE_PROTO)

	/**
	  * `Writeable` for `scalaPB data class` values.
	  */
	implicit def writeableOf_Protobuf: Writeable[GeneratedMessage] = {
		Writeable(a => ByteString(a.toByteArray),OPTION_MIME_TYPE_PROTO)
	}

	/**
	  * Parse the body as `scalaPB data class`.
	  * {{{
	  * import com.kos.dataconversion.play.PlayProtobuf._
	  * class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
	  * 	implicit def controller=this
	  * 	def message = Action(parseProto(Person)) { request: Request[Person] ⇒
	  * 	...
	  * 	}
	  * }
	  * }}}
	  * @param gm scalaPB data companion class
	  * @param controller AbstractController
	  * @param executor ExecutionContext
	  * @tparam A result class
	  * @return scalaPB data class
	  */
	def parseProto[A <: com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[A]]
	(gm: GeneratedMessageCompanion[A])(implicit controller:BaseControllerHelpers, executor:ExecutionContext):BodyParser[A] = controller.parse.using{ request ⇒
		controller.parse.raw.map(x ⇒ x.asBytes().map {bytes ⇒ gm.parseFrom(bytes.toArray)
		}.getOrElse(throw new ParseProtoException("no parse proto")))
	}

}