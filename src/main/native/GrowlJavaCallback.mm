/*
 * Copyright (c) 2010, Michael Stringer
 * 
 * This file is part of Growl Java.
 * 
 * Growl Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Growl Java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Growl Java. If not, see
 * <http://www.gnu.org/licenses/>.
 */

#import <jni.h>
#import <Foundation/Foundation.h>
#import <AppKit/AppKit.h>

#import "GrowlJavaCallback.h"

static JavaVM *cachedJVM = NULL;
@implementation GrowlJavaCallback

- (id) initWithCallback : (jobject) callback {
	self = [super init];
	
	_callbackObject = callback;

	return self;
}

- (void) growlNotificationWasClicked : (NSNotification *)notification {
	// get the callback id
	NSDictionary	*callbackDictionary = (NSDictionary *)[notification userInfo];
	NSString		*callbackId = (NSString *)[callbackDictionary objectForKey:@"ClickedContext"];
	const char      *nativeString;
	jstring			convertedString;

	if (cachedJVM == NULL) {
		jsize jvmCount;
		jint jvmError = JNI_GetCreatedJavaVMs(&cachedJVM, 1, &jvmCount);
		
		if (jvmError != 0) {
			// TODO panic like crazy
			NSLog(@"Error error error");
		}
	}

	if (cachedJVM != NULL) {
		JNIEnv *env = NULL;
		cachedJVM->GetEnv((void **)&env, JNI_VERSION_1_2);
		
		_callbackClass = env->GetObjectClass(_callbackObject);
		_callbackMethod = env->GetMethodID(_callbackClass, "fireCallbacks", "(Ljava/lang/String;)V");

		nativeString = [callbackId UTF8String];
		convertedString = env->NewStringUTF(nativeString);

		// fire off the callback
		env->CallVoidMethod(_callbackObject, _callbackMethod, convertedString);
	}
}

@end