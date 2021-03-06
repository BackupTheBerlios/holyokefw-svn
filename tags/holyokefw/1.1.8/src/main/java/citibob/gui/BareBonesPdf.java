///*
//Holyoke Framework: library for GUI-based database applications
//This file Copyright (c) 2006-2008 by Robert Fischer
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
//*/
///////////////////////////////////////////////////////////
////  Bare Bones Browser Launch                          //
////  Version 1.5                                        //
////  December 10, 2005                                  //
////  Supports: Mac OS X, GNU/Linux, Unix, Windows XP    //
////  Example Usage:                                     //
////     String url = "http://www.centerkey.com/";       //
////     BareBonesBrowserLaunch.openURL(url);            //
////  Public Domain Software -- Free to Use as You Like  //
///////////////////////////////////////////////////////////
//
//package citibob.gui;
//
//import java.lang.reflect.Method;
//import javax.swing.JOptionPane;
//import java.io.*;
//
//public class BareBonesPdf {
//
//   private static final String errMsg = "Error attempting to launch web browser";
//
//   public static void view(java.io.File f) {
//	   String url = f.getPath();
//      String osName = System.getProperty("os.name");
//      try {
//         if (osName.startsWith("Mac")) {
////            Class fileMgr = Class.forName("com.apple.eio.FileManager");
////            Method openURL = fileMgr.getDeclaredMethod("open",
////               new Class[] {String.class});
////            openURL.invoke(null, new Object[] {url});
//            Runtime.getRuntime().exec("open " + url);
//            }
//         else if (osName.startsWith("Windows")) {
//            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
//	} else { //assume Unix or Linux
//            String[] browsers = {
//               "acroread", "kpdf"  };
//            String browser = null;
//            for (int count = 0; count < browsers.length && browser == null; count++)
//               if (Runtime.getRuntime().exec(
//                     new String[] {"which", browsers[count]}).waitFor() == 0)
//                  browser = browsers[count];
//            if (browser == null)
//               throw new Exception("Could not find PDF Viewer");
//            else
//               Runtime.getRuntime().exec(new String[] {browser, url});
//            }
//         }
//      catch (Exception e) {
//         JOptionPane.showMessageDialog(null, errMsg + ":\n" + e.getLocalizedMessage());
//         }
//      }
//public static void main(String[] args)
//{
//    view(new File("c:\\x.pdf"));
//}
//   }
