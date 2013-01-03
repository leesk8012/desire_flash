#if(true)
#define LOCAL_LOG
#define LOCAL_LOGD
#endif

#include <string.h>
#include <jni.h>

#ifdef LOCAL_LOG
#include <android/log.h>
#endif

#include <stdlib.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <sys/stat.h>
#include <sys/syscall.h>
#include "msm_camera.h"
#include "desireflash.h"

/*
* changes.
* msm_camsensor_info info; -> struct msm_camsensor_info info;
* env->NewStringUTF(pszName); -> (*env)->NewStringUTF(env,pszName);
*/

int	CtrlCameraLED(int nMode)
{
	int		fd;
	int		nRet;

	fd = open("/dev/msm_camera/config0",O_RDWR | O_NONBLOCK);
	if(fd < 0)
		return	0;

	struct msm_camsensor_info info;

	memset(&info,0,sizeof(info));
	nRet = ioctl(fd,MSM_CAM_IOCTL_GET_SENSOR_INFO,&info);
	if(nRet < 0 || info.flash_enabled == 0)
	{
		close(fd);
		return	0;
	}

	nRet = ioctl(fd,MSM_CAM_IOCTL_FLASH_LED_CFG,&nMode);
	close(fd);
	if(nRet < 0)
		return	0;

	return	1;
}

int	IsSupportCameraLED(void)
{
	int		fd;
	int		nOnOff;		//uint_32
	int		nRet;
	struct msm_camsensor_info info;

	fd = open("/dev/msm_camera/config0",O_RDWR | O_NONBLOCK);
	if(fd < 0)
		return	0;

	memset(&info,0,sizeof(info));
	nRet = ioctl(fd,MSM_CAM_IOCTL_GET_SENSOR_INFO,&info);
	close(fd);
	if(nRet < 0)
		return	0;

	if(info.flash_enabled)
		return	1;
	return	0;
}

// 
int	GetCameraSensorName(char* pszName,int nLen)
{
	int		fd;
	int		nOnOff;		//uint_32
	int		nRet;
	struct msm_camsensor_info info;

	if(nLen < MAX_SENSOR_NAME)
		return	0;

	fd = open("/dev/msm_camera/config0",O_RDWR | O_NONBLOCK);
	if(fd < 0)
		return	0;

	memset(&info,0,sizeof(info));
	nRet = ioctl(fd,MSM_CAM_IOCTL_GET_SENSOR_INFO,&info);
	close(fd);
	if(nRet < 0)
		return	0;

	strcpy(pszName,info.name);

	return	1;
}

/*
 * Class:     com_sopeng_desireflash_DesireLED
 * Method:    NativeIsSupportCameraLED
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_sopeng_desireflash_DesireLED_NativeIsSupportCameraLED
  (JNIEnv * env, jobject obj)
{
	if(IsSupportCameraLED)
		return	JNI_TRUE;
	return	JNI_FALSE;
}

/*
 * Class:     com_sopeng_desireflash_DesireLED
 * Method:    NativeCtrlCameraLED
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_sopeng_desireflash_DesireLED_NativeCtrlCameraLED
  (JNIEnv * env, jobject obj, jint nMode)
{
	if(CtrlCameraLED(nMode))
		return	JNI_TRUE;
	return	JNI_FALSE;
}

/*
 * Class:     com_sopeng_desireflash_DesireLED
 * Method:    NativeGetSensorName
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_sopeng_desireflash_DesireLED_NativeGetSensorName
  (JNIEnv * env, jobject obj)
{
	char	pszName[MAX_SENSOR_NAME];

	if(GetCameraSensorName(pszName,MAX_SENSOR_NAME) == 0)
		return	(*env)->NewStringUTF(env, "Failed");

	return (*env)->NewStringUTF(env, pszName);
}

