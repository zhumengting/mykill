package com.zmt.mykill.utils;

/**
 * 通用异常框架
 */
public class CommonException extends RuntimeException {

   /**
    * 业务异常Code 例：密码错误
    */
   private int errorCode;

   /**
    * 错误提示
    */
   private String message;

   /**
    * 构造异常
    */
   public CommonException() {
   }


   /**
    * 构造异常
    *
    * @param message 错误信息
    */
   public CommonException(String message) {
      super(message);
      this.message = message;
   }

   /**
    * 构造异常
    *
    * @param errorCode 具体业务代码
    * @param message   错误信息
    */
   public CommonException(int errorCode, String message) {
      super(message);
      this.errorCode = errorCode;
      this.message = message;
   }

   /**
    * 获取错误码Code
    *
    * @return Code
    */
   public int getCode() {
      return errorCode;
   }

}
