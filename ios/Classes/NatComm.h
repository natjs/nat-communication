//
//  NatComm.h
//
//  Created by huangyake on 17/1/7.
//  Copyright © 2017 Nat. All rights reserved.
//


#import <Foundation/Foundation.h>
#import <MessageUI/MessageUI.h>

@interface NatComm : NSObject<MFMessageComposeViewControllerDelegate,MFMailComposeViewControllerDelegate>

typedef void (^NatCallback)(id error, id result);

+ (NatComm *)singletonManger;
//打电话
- (void)call:(NSString *)phone :(NatCallback)callback;
//发邮件 程序内
- (void)mail:(NSArray *)mail :(NSDictionary*)params :(NatCallback)callback;
//发短息 程序内
- (void)sms:(NSArray *)phone :(NSString *)text :(NatCallback)callback;

@end
