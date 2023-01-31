import time
import cv2
from simple_faceres import SimpleFacerec

# Encode faces from a folder
sfr = SimpleFacerec()
sfr.load_encoding_images("D:\IDE\Git\DuAn1\ManagePhoneStore\Images\RecognitionFace")

# Load Camera
cap = cv2.VideoCapture(0)


while True:
    ret, frame = cap.read()
    # Detect Faces
    face_locations, face_names = sfr.detect_known_faces(frame)
    for face_loc, name in zip(face_locations, face_names):
        y1, x2, y2, x1 = face_loc[0], face_loc[1], face_loc[2], face_loc[3]
        if(name=="Unknown"):
         cv2.putText(frame, name,(x1, y1 - 10), cv2.FONT_HERSHEY_DUPLEX, 1, (0,0,200), 1)
         cv2.rectangle(frame, (x1, y1), (x2, y2),(0,0,200), 4)
        else:
         cv2.putText(frame, name,(x1, y1 - 10), cv2.FONT_HERSHEY_DUPLEX, 1, (100, 255, 50), 1)
         cv2.rectangle(frame, (x1, y1), (x2, y2),(100, 255, 50), 4)

    cv2.imshow("Frame", frame)
    #print(face_names)
    try:
     if( face_names!=[]):
        file = open("D:\IDE\Git\DuAn1\ManagePhoneStore\src\FaceRecognitionByPython\Record.txt", "w") 
        
        for i in face_names:
            print(i)
            if (i=='MinhHau'):
             file.write("MinhHau")
             print("Write file successfully")
            elif (i=='NgocHoai'):
             file.write("NgocHoai")
             print("Write file successfully")
            elif (i=='nhanvienkithuat'):
              file.write("nhanvienkithuat")  
              print("Write file successfully")
            elif (i=='nhanvienbanhang'):
              file.write("nhanvienbanhang")  
              print("Write file successfully")
            elif (i=='nhanvientiepthi'):
              file.write("nhanvientiepthi")  
              print("Write file successfully")
            elif (i=='Unknown'):
              file.write("Unknown")  
              print("Write file successfully")
        file.close()
        time.sleep(1)
        break

     key = cv2.waitKey(2)
     if key == 13:
        if( face_names==[] ):
         file = open("D:\IDE\Git\DuAn1\ManagePhoneStore\src\FaceRecognitionByPython\Record.txt", "w") 
         for i in face_names:
             file.write("Unknown")
         file.close()
        break
    
    except:
        input("Errol")
cap.release()
cv2.destroyAllWindows()