const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendStatusNotifications = functions.firestore
   .document('walk_plans/{walksplanId}/rsvp_status/{rsvpstatusid}')
   .onUpdate((change, context) => {
     // Get an object with the current document value.
     // If the document does not exist, it has been deleted.
     const document = change.exists ? change.after.data() : null;

     if (document) {
       var message = {
         notification: {
           title: 'Walk ' + document.status,
           body: document.user + ' ' + document.status + 'your walk!'
         },
         topic: context.params.rsvpstatusid
       };

       return admin.messaging().send(message)
         .then((response) => {
           // Response is a message ID string.
           console.log('Successfully sent message:', response);
           return response;
         })
         .catch((error) => {
           console.log('Error sending message:', error);
           return error;
         });
     }

     return "document was null or emtpy";
   });

exports.sendScheduleNotifications = functions.firestore
   .document('walk_plans/{walksplanId}/is_scheduled/{is_scheduledid}')
   .onUpdate((change, context) => {
     // Get an object with the current document value.
     // If the document does not exist, it has been deleted.
     const document = change.exists ? change.after.data() : null;

     if (document) {
       var message = {
         notification: {
           title: 'Walk ' + document.status,
           body: document.user + ' ' + document.status + 'your walk!'
         },
         topic: context.params.is_scheduledid
       };

       return admin.messaging().send(message)
         .then((response) => {
           // Response is a message ID string.
           console.log('Successfully sent message:', response);
           return response;
         })
         .catch((error) => {
           console.log('Error sending message:', error);
           return error;
         });
     }

     return "document was null or emtpy";
   });
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
