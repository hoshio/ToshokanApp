package controllers;

import java.util.*;
//modelsパッケージ使うよね
import models.*;

//若干おまじない
import play.*;
import play.data.*;
import static play.data.Form.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result index() {
		List<Message> msgs = Message.find.all();
		return ok(index.render("please ser from", msgs));
	}

    public static Result add() {
        Form<Message> f = new Form(Message.class);
        return ok(add.render("投稿フォーム", f));
    }
    
    public static Result create() {
        //requestからフォームインスタンスを取得
    	Form<Message> f = new Form(Message.class).bindFromRequest();
    	if (!f.hasErrors()) {
            //フォームにエラーがない場合、Messageインスタンスを取得
    		Message data = f.get();
            //Messageインスタンスを保存
    		data.save();
            //Welcomeページにリダイレクト
    		return redirect("/");
    	} else {
    		return badRequest(add.render("ERROR", f));
    	}
    }

    public static Result setitem() {
        Form<Message> f = new Form(Message.class);
        return ok(item.render("ID番号を入力",f));
    }

    public static Result edit() {
        Form<Message> f = new Form(Message.class).bindFromRequest();
        if (!f.hasErrors()) {
            Message obj = f.get();
            Long id = obj.id;
            obj = Message.find.byId(id);
            if (obj != null) {
                f = new Form(Message.class).fill(obj);
                return ok(edit.render("ID=" + id + "の投稿を編集", f));
            } else {
                return ok(item.render("ERROR:IDの投稿が見つかりません",f));
            }
        } else {
            return ok(item.render("ERROR:入力に問題があります",f));
        }
    }

    public static Result update() {
        Form<Message> f = new Form(Message.class).bindFromRequest();
        if (!f.hasErrors()) {
            Message data = f.get();
            System.out.println(data.toString());
            data.update();
            return redirect("/");
        } else {
            return ok(edit.render("ERROR:再度入力してください",f));
        }
    }

    public static Result delete() {
        Form<Message> f = new Form(Message.class);
        return ok(delete.render("削除するID番号",f));
    }

    public static Result remove() {
        Form<Message> f = new Form(Message.class).bindFromRequest();
        if (!f.hasErrors()) {
            Message obj = f.get();
            Long id = obj.id;
            obj = Message.find.byId(id);
            if (obj != null) {
                obj.delete();
                return redirect("/");
            } else {
                return ok(delete.render("ERROR:そのID番号は見つかりません",f));
            }
        } else {
            return ok(delete.render("ERROR:入力エラーが怒りました", f));
        }
    }
}
