import java.awt.*;

class NinjaBullet extends MovingObject{//EnemyAクラスのオブジェクトが発車する弾のクラス
Image syurikenn;//画像を入れる場所
NinjaBullet(){

w=h=7;//半径
dx=0;dy=-5;//速さ
hp=0;
}
void cGetImage (GameMaster ap){//オブジェクトの画像を読み込むメソッド

syurikenn = Toolkit.getDefaultToolkit().createImage("122558s.png");//画像の読み込み
  }
void move(Graphics buf,int apWidth, int apHeight){//オブジェクトの位置を更新するメソッド

if(hp>0){
buf.drawImage(syurikenn, x, y, x+60, y+90, 0, 0, 84, 79, null);//画像貼り付け

if(y>0&&y<apHeight &&x>0 &&x<apWidth)//弾が画面内の時
y=y-dy;//更新
else hp=0;

}
}

void revive(int x, int y){//x、yはEnemyAの位置
this.x=x;
this.y=y;
hp=1;//球をアクティブにする
}
boolean collisionCheck(MovingObject obj){

if(Math.abs(this.x-obj.x)<=(this.w+obj.w) && Math.abs(this.y-obj.y)<=(this.h+obj.h)){//当たり判定
  this.hp--;//自分のHPを減らす
  obj.hp--;//相手のHPを減らす
  return true;
  }

else if(Math.abs(this.x-obj.x)<=(this.w+obj.w) && Math.abs(this.y-obj.y)<=(this.h+obj.h+40)){//当たり判定
  this.hp--;//自分のHPを減らす
  obj.hp--;//相手のHPを減らす
  return true;
  }






  else
  return false;
  }



 


}


