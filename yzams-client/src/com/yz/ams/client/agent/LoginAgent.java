 /*
 * LoginAgent.java
 * 
 * Copyright(c) 2007-2015 by Yingzhi Tech
 * All Rights Reserved
 * 
 * Created at 2015-03-26 16:33:09
 */
package com.yz.ams.client.agent;

import com.nazca.io.httpdao.HttpClientContext;
import com.nazca.io.httprpc.HttpRPC;
import com.nazca.io.httprpc.HttpRPCException;
import com.nazca.usm.client.ConfigManager;
import com.nazca.usm.client.ServiceConfig;
import com.nazca.usm.client.UsmsClientContext;
import com.nazca.usm.client.service.async.agent.ClientClockAgent;
import com.nazca.usm.common.LoginException;
import com.nazca.usm.common.LoginResult;
import com.nazca.usm.model.USMSUser;
import com.nazca.usm.service.rpc.LoginRPCService;
import com.yz.ams.client.ClientContext;
import com.yz.ams.consts.ErrorCode;
import com.yz.ams.rpc.LoginAuthService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 登录代理
 *
 * @author Qiu Dongyue
 */
public class LoginAgent extends AbstractAgent<USMSUser> {

    private Log log = LogFactory.getLog(LoginAgent.class);
    private String loginName;
    private String password;

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected USMSUser doExecute() throws HttpRPCException {
//        if ("sys".equals(loginName) && "sys".equals(password)) {
//            USMSUser user = new USMSUser("sys");
//            user.setName("管理员");
//            new TimeFairy().sleepIfNecessary();
//            return user;
//        } else {
//            throw new HttpRPCException("用户认证失败！", 990010);
//        }
        log.info(ClientContext.getUsmsServerRPCURL());
        try {
            //通过系统设置的用户认证服务器地址获取usm service
            LoginRPCService usmLoginServ = HttpRPC.getService(LoginRPCService.class, ClientContext.getUsmsServerRPCURL(), true);
            //登录
            LoginResult result = usmLoginServ.login(loginName, password);
            USMSUser usmUser = result.getUser();
            //判断该用户是否拥有登录该系统的权限
            if (usmUser.getRoleSet().size() == 0 && usmUser.getPermissionSet().size() == 0) {
                throw new HttpRPCException("您没有任何权限，不能登录", ErrorCode.LACK_OF_ROLES);
            }
            //获取usm上下文，系统设置用户认证服务器地址需和用户目录下.usms-client对应模块文件下的地址一样
            HttpClientContext usmContext = HttpRPC.getClientContext(ClientContext.getUsmsServerRPCURL());
            LoginAuthService rServ = HttpRPC.getService(LoginAuthService.class, ClientContext.getSysServerRPCURL(), true);
            rServ.auth(usmUser.getId(), usmContext.getUserToken());
//            TimeServiceAgent.getInstance().startSyncTime();
//            USMSessionKeeperAgent.getAgent().startKeepSession();
//            RPCSessionHandler.startListenSession();
            //记录到usm服务端
            UsmsClientContext.setUsmsServerAddr(ClientContext.getUsmsServerRPCURL());
            UsmsClientContext.setCurrUser(usmUser);
            UsmsClientContext.setCurOrg(usmUser.getOrg());
            //保存到usm客户端
            ServiceConfig.setUsmsServerURL(ClientContext.getUsmsServerRPCURL());
            //设置是否自己管理usm
            ConfigManager.putConfig(ConfigManager.KEY_SELF_MGMT, ConfigManager.VALUE_TRUE);
            ConfigManager.putConfig(ConfigManager.KEY_HAS_ORG, ConfigManager.VALUE_TRUE);

//            TimeServiceAgent.getInstance().startSyncTime();
            ClientClockAgent.getInstance().startSyncTime();
            ClientContext.setUser(usmUser);
            ClientContext.setPassword(password);
            ClientContext.setUsmsToken(usmContext.getUserToken());
            
            log.info("-----usm token = " + usmContext.getUserToken());
            return usmUser;
        } catch (LoginException ex) {
            ex.printStackTrace();
            throw new HttpRPCException(ex.getMessage(), ex.getCode()
                    + ErrorCode.USMS_ERROR_CODE_START);
        }
    }
}
