#!/usr/bin/python
import sys

if __name__ == '__main__':
    if len(sys.argv) < 3:
	print 'usage:<train> <output>'
	exit(-1)
    ftrain = sys.argv[1]
    fo = open( sys.argv[2], 'w' )
    cnt = 0
    last_uid = -1
    for line in open( ftrain ):
        uid = int( line.split()[0] )
        if uid != last_uid:
            if cnt != 0:
                fo.write( '%d 0\n' % cnt )
            cnt = 0
            last_uid = uid
        cnt += 1

    if cnt != 0:
        fo.write( '%d 0\n' % cnt )
    print cnt
    fo.close()
