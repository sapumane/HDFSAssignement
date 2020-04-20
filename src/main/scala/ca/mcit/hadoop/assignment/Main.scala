package ca.mcit.hadoop.assignment

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import scala.io.Source

object Main extends App{

  var conf = new Configuration()
  conf.addResource(new  Path("/home/bd-user/opt/hadoop-2.7.3/etc/cloudera/core-site.xml"))
  conf.addResource(new  Path("/home/bd-user/opt/hadoop-2.7.3/etc/cloudera/hdfs-site.xml"))
  var fs: FileSystem = FileSystem.get(conf)
  /*Print URI*/
  println(fs.getUri)
  /*Print Current Working Directory*/
  println( fs.getWorkingDirectory)
  val path = new Path("/user/fall2019/sappu")
  /*2. List the content of /user/2019*/
  fs.listStatus(new Path("/user/fall2019/"))
    .foreach(println)
  /*4.a Check Folder exists or not */
  if(fs.exists(path)) {
    println("i found my folder")
  }
  else {
    println("i failed in the previous practice")
  }
  /* Delete Folder */
  if (fs.exists(path)){
    println("deleting file : " + path)
    fs.delete(path,true)
  }
  else {
    println("File/Directory" + path + " does not exist")
       }
  /*Create Folder sappu */
  fs.mkdirs(new Path("/user/fall2019/sappu"))
  if(fs.exists(new Path("/user/fall2019/sappu"))) {
    println("successfully created " )
  }
  /*List Folder */
  fs.listStatus(new Path("/user/fall2019/sappu"))
    .foreach(println)
  /* Subfolder STM */
  fs.mkdirs(new Path("/user/fall2019/sappu/STM"))
  println("subfolder created")
  /*Copy Stops.txt */
  val srcPath = new Path("/home/bd-user/Downloads/stops.txt")
  val destPath = new Path("/user/fall2019/sappu/STM")
  fs.copyFromLocalFile(srcPath, destPath)
  println("file uploaded")
  /* Make Copy of stop2.txt */
  val src= new Path("/user/fall2019/sappu/STM/stops.txt")
  val dest= new Path("/user/fall2019/sappu/STM/stops2.txt")
  FileUtil.copy(src.getFileSystem(conf),src,dest.getFileSystem(conf),dest,false,conf)
  println("successfully created copy of stops.txt")
  /* Rename stops2.txt to stops.csv*/
  fs.rename(dest, new Path("/user/fall2019/sappu/STM/stops.csv"))
  val getlines: Unit = Source
    .fromInputStream( fs.open( new Path( "/user/fall2019/sappu/STM/stops.csv") ) )
    .getLines().slice(0,6).foreach( println )
}
