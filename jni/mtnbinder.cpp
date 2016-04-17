/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#define LOG_TAG "Exec"

#include "jni.h"
#include "utils/Log.h"
#include "utils/misc.h"
#include "android_runtime/AndroidRuntime.h"

#include <sys/types.h>
#include <sys/ioctl.h>
#include <sys/wait.h>
#include <errno.h>
#include <fcntl.h>
#include <stdlib.h>
#include <unistd.h>

static void com_mtn_javainject_CopyFile_callHook(JNIEnv *env, jclass,
		jstring path1, jstring path2, jstring className, jstring methodName) {
	jclass classloaderClass = (*env)->FindClass(env, "java/lang/ClassLoader");
	jmethodID getsysloaderMethod = (*env)->GetStaticMethodID(env,
			classloaderClass, "getSystemClassLoader",
			"()Ljava/lang/ClassLoader;");
	jobject loader = (*env)->CallStaticObjectMethod(env, classloaderClass,
			getsysloaderMethod);

	jstring dexpath = path1; // = (*env)->NewStringUTF(env, "dex文件路径");
	jstring dex_odex_path = path2; //= (*env)->NewStringUTF(env,"odex文件路径");
	jclass dexLoaderClass = (*env)->FindClass(env,
			"dalvik/system/DexClassLoader");
	jmethodID initDexLoaderMethod =
			(*env)->GetMethodID(env, dexLoaderClass, "<init>",
					"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V");
	jobject dexLoader = (*env)->NewObject(env, dexLoaderClass,
			initDexLoaderMethod, dexpath, dex_odex_path, NULL, loader);

	jmethodID findclassMethod = (*env)->GetMethodID(env, dexLoaderClass,
			"findClass", "(Ljava/lang/String;)Ljava/lang/Class;");
	jstring javaClassName = className; //(*env)->NewStringUTF(env,"加载类");
	jclass javaClientClass = (*env)->CallObjectMethod(env, dexLoader,
			findclassMethod, javaClassName);

	const char* chars = env->GetStringUTFChars(methodName, NULL);

	jmethodID start_inject_method = (*env)->GetStaticMethodID(env,
			adInjectClient_class, chars, "()V");
	(*env)->CallStaticVoidMethod(env, javaClientClass, start_inject_method);
}

static void com_mtn_javainject_CopyFile_callHook(JNIEnv *env, jclass) {

}

static const char *classPathName = "com/mtn/javainject/CopyFile";

static JNINativeMethod method_table[] =
		{
				{ "callHook",
						"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
						(void*) com_mtn_javainject_CopyFile_callHook } };

/*
 * Register several native methods for one class.
 */
static int registerNativeMethods(JNIEnv* env, const char* className,
		JNINativeMethod* gMethods, int numMethods) {
	jclass clazz;

	clazz = env->FindClass(className);
	if (clazz == NULL) {
		LOGE("Native registration unable to find class '%s'", className);
		return JNI_FALSE;
	}
	if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
		LOGE("RegisterNatives failed for '%s'", className);
		return JNI_FALSE;
	}

	return JNI_TRUE;
}

/*
 * Register native methods for all classes we know about.
 *
 * returns JNI_TRUE on success.
 */
static int registerNatives(JNIEnv* env) {
	if (!registerNativeMethods(env, classPathName, method_table,
			sizeof(method_table) / sizeof(method_table[0]))) {
		return JNI_FALSE;
	}

	return JNI_TRUE;
}

// ----------------------------------------------------------------------------

/*
 * This is called by the VM when the shared library is first loaded.
 */

typedef union {
	JNIEnv* env;
	void* venv;
} UnionJNIEnvToVoid;

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
	UnionJNIEnvToVoid uenv;
	uenv.venv = NULL;
	jint result = -1;
	JNIEnv* env = NULL;

	LOGI("JNI_OnLoad");

	if (vm->GetEnv(&uenv.venv, JNI_VERSION_1_4) != JNI_OK) {
		LOGE("ERROR: GetEnv failed");
		goto bail;
	}
	env = uenv.env;
	if (registerNatives(env) != JNI_TRUE) {
		LOGE("ERROR: registerNatives failed");
		goto bail;
	}

	result = JNI_VERSION_1_4;

	bail: return result;
}
