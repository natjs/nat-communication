//
//  NatCommunication.h
//
//  Created by huangyake on 17/1/7.
//  Copyright © 2017 Instapp. All rights reserved.
//


#import <Foundation/Foundation.h>
#import <MessageUI/MessageUI.h>

@interface NatCommunication : NSObject<MFMessageComposeViewControllerDelegate,MFMailComposeViewControllerDelegate>

typedef void (^NatCallback)(id error, id result);

+ (NatCommunication *)singletonManger;
- (void)call:(NSString *)phone :(NatCallback)callback;
- (void)mail:(NSArray *)mail :(NSDictionary*)params :(NatCallback)callback;
- (void)sms:(NSArray *)phone :(NSString *)text :(NatCallback)callback;

@end
