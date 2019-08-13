package com.myhexin.consulAdmin.common.reslut;

public class ResultFactory {

    public static ResultDTO success(Object object){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(ResultEnum.SUCESS.getCode());
        resultDTO.setMsg(ResultEnum.SUCESS.getMsg());
        resultDTO.setData(object);
        return resultDTO;
    }
    public static ResultDTO success(){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(ResultEnum.SUCESS.getCode());
        resultDTO.setMsg(ResultEnum.SUCESS.getMsg());
        return resultDTO;
    }
    public static ResultDTO fail(){
        ResultDTO resultDTO=new ResultDTO();
        resultDTO.setCode(ResultEnum.FAIL.getCode());
        resultDTO.setMsg(ResultEnum.FAIL.getMsg());
        return resultDTO;
    }

    private static ResultDTO getResultDTO(Integer code,String msg,Object object){
        ResultDTO resultDTO=new ResultDTO(code,msg,object);
        return resultDTO;
    }
}
